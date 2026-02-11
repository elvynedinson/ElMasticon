package com.evydev.elmasticon.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

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
}
