package com.evydev.elmasticon.ui.login

import android.content.Context
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evydev.elmasticon.auth.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.text.CharCategory

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null
)

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {

    private val _loginFormState = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginFormState

    private val _state = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val state: StateFlow<LoginUiState> = _state

    fun signInWithGoogle(context: Context, onResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            repository.signInWithGoogle(context)
                .onSuccess { email ->
                    val uid = repository.getCurrentUserUid()
                    if (uid != null && email != null) {
                        val exists = repository.checkIfUserExists(uid)
                        onResult(email, exists)
                    }
                }
                .onFailure { exception ->
                    if (exception is GetCredentialCancellationException) {
                        _state.value = LoginUiState.Idle
                    } else {
                        _state.value = LoginUiState.Error(
                            exception.message ?: "Error al conectar con Google"
                        )
                    }
                }
        }
    }

    fun onEmailChange(newEmail: String){
        val filtered = filterSecureText(newEmail).replace(" ", "")
        _loginFormState.value = _loginFormState.value.copy(email = filtered)
    }

    fun onPasswordChange(newPassword: String){
        val filteredPassword = filterSecureText(newPassword)
        _loginFormState.value = _loginFormState.value.copy(password = filteredPassword)
    }

    fun validateFormLogin(): Boolean{

        val currentEmail = _loginFormState.value.email
        val currentPassword = _loginFormState.value.password

        val emailError = if (currentEmail.isBlank()){
            "El correo es obligatorio"
        } else{
            null
        }

        val passwordError = if (currentPassword.isBlank()){
            "La contraseña es obligatoria"
        } else{
            null
        }

        _loginFormState.value = _loginFormState.value.copy(
            emailError = emailError,
            passwordError = passwordError
        )
        return emailError == null && passwordError == null
    }

    fun filterSecureText(text: String): String {
        return text.filter { char ->
            char.category != CharCategory.OTHER_SYMBOL && char.category != CharCategory.SURROGATE
        }
    }

    fun login(){
        viewModelScope.launch {
            if (validateFormLogin()){
                _state.value = LoginUiState.Loading
                val result = repository.login(_loginFormState.value.email, _loginFormState.value.password)
                result
                    .onSuccess {
                        _state.value = LoginUiState.Success
                    }
                    .onFailure {
                        _state.value = LoginUiState.Error(it.message ?: "Error Desconocido")
                    }
            }
        }
    }
}
