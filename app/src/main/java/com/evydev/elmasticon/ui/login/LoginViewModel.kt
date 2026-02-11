package com.evydev.elmasticon.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evydev.elmasticon.auth.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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