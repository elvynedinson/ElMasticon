package com.evydev.elmasticon.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): Result<Unit>{
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)

        }catch (e: FirebaseAuthInvalidUserException){
            Result.failure(Exception("Este correo no está registrado"))

        } catch (e: FirebaseAuthInvalidCredentialsException){
            Result.failure(Exception("Correo o contraseña incorrecta"))

        } catch (e: Exception){
            Result.failure(Exception("Ocurrió un error, intenta nuevamente"))
        }
    }
}
