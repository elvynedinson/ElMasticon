package com.evydev.elmasticon.auth.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.evydev.elmasticon.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    suspend fun saveUserProfile(uid: String, name: String, phone: String, email: String): Result<Unit>{
        return try {
            val user = hashMapOf(
                "uid" to uid,
                "name" to name,
                "phone" to phone,
                "email" to email,
                "createAt" to Timestamp.now()
            )

            db.collection("users").document(uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    fun getCurrentUserUid(): String? = auth.currentUser?.uid

    suspend fun signInWithGoogle(context: Context): Result<String> {
        return try {
            val credentialManager = CredentialManager.create(context)

            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()

            val request: GetCredentialRequest  = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            val email = googleIdTokenCredential.id

            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
            auth.signInWithCredential(firebaseCredential).await()
            Result.success(googleIdTokenCredential)

            Result.success(email)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun checkIfUserExists(uid: String): Boolean {
        return try {
            val document = db.collection("users").document(uid).get().await()
            document.exists()
        } catch (e: Exception){
            false
        }
    }

    suspend fun login(email: String, password: String): Result<Unit>{
        return try {

          val result = auth.signInWithEmailAndPassword(email, password).await()

            val user = result.user

            if (user != null && user.isEmailVerified) {
                Result.success(Unit)
            } else {
                auth.signOut()
                Result.failure(Exception("Por favor, verifica tu correo electrónico"))
            }

        }catch (e: FirebaseAuthInvalidUserException){
            Result.failure(Exception("Este correo no está registrado"))

        } catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("Correo o contraseña incorrecta"))

        } catch (e: Exception){
            Result.failure(Exception("Ocurrió un error, intenta nuevamente"))
        }
    }

    suspend fun register(email: String, password: String): Result<Unit>{
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()

            result.user?.sendEmailVerification()?.await()

            Result.success(Unit)

        }catch (e: FirebaseAuthUserCollisionException){
            Result.failure(Exception("Correo en uso"))

        } catch (e: FirebaseAuthWeakPasswordException){
            Result.failure(Exception("La contraseña es muy débil"))

        } catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("El formato del correo es inválido"))

        } catch (e: Exception){
            Result.failure(Exception("Error al crear la cuenta, intenta de nuevo"))
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(Exception("No se pudo enviar el correo. Intenta de nuevo."))
        }
    }

}
