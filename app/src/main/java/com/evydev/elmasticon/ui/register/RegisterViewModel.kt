package com.evydev.elmasticon.ui.register

import android.content.Context
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evydev.elmasticon.auth.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.CharCategory

data class RegisterFormState(
    val name: String = "",
    val nameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(RegisterFormState())
    val formState: StateFlow<RegisterFormState> = _formState

    private val _state = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val state: StateFlow<RegisterUiState> = _state

    fun signInWithGoogle(context: Context, onResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            repository.signInWithGoogle(context)
                .onSuccess { email ->
                    val uid = repository.getCurrentUserUid()
                    if (uid != null && email != null){
                        val exists = repository.checkIfUserExists(uid)
                        onResult(email, exists)
                    }
                }
                .onFailure { exception ->
                    if (exception is GetCredentialCancellationException) {
                        _state.value = RegisterUiState.Idle
                    } else {
                        _state.value = RegisterUiState.Error(
                            exception.message ?: "Error al conectar con Google"
                        )
                    }
                }
        }
    }

    fun onNameChanged(newName: String) {
        val filteredName = newName.filter { it.isLetter() || it.isWhitespace() }
        val capitalizedName = filteredName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
        _formState.value = _formState.value.copy(name = capitalizedName)
    }

    fun onPhoneChange(newPhone: String) {
        val filterPhone = newPhone.filter { it.isDigit() }.take(9)
        _formState.value = _formState.value.copy(phone = filterPhone)
    }

    fun onEmailChange(newEmail: String) {
        val filterEmail = filterSecureText(newEmail).replace(" ", "")
        _formState.value = _formState.value.copy(email = filterEmail)
    }

    fun onPasswordChange(newPassword: String) {
        val filtered = filterSecureText(newPassword)
        _formState.value = _formState.value.copy(password = filtered)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        val filtered = filterSecureText(newConfirmPassword)
        _formState.value = _formState.value.copy(confirmPassword = filtered)
    }

    fun filterSecureText(text: String): String {
        return text.filter { char ->
            char.category != CharCategory.OTHER_SYMBOL && char.category != CharCategory.SURROGATE
        }
    }

    fun validateForm(): Boolean {
        val currentName = _formState.value.name
        val currentPhone = _formState.value.phone
        val currentEmail = _formState.value.email
        val currentPassword = _formState.value.password
        val currentConfirmPassword = _formState.value.confirmPassword

        val nameError = if (currentName.isBlank()) {
            "El nombre es obligatorio"
        } else {
            null
        }

        val phoneError = if (currentPhone.length < 9) {
            "El número debe tener al menos 9 digitos"
        } else {
            null
        }

        val emailError = if (currentEmail.isBlank()) {
            "Este campo no puede estar vacio"
        } else {
            null
        }

        val passwordError = if (currentPassword.isBlank()) {
            "Este campo no puede estar vacio"
        } else if (currentPassword.length < 7) {
            "La contraseña debe tener al menos 6 caracteres"
        } else {
            null
        }

        val confirmPasswordError = if (currentConfirmPassword.isBlank()) {
            "El campo no puede estar vacio"
        } else if (currentPassword != currentConfirmPassword) {
            "Las contraseñas no coinciden"
        } else {
            null
        }

        _formState.value = _formState.value.copy(
            nameError = nameError,
            phoneError = phoneError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError
        )

        return nameError == null && phoneError == null && emailError == null && passwordError == null && confirmPasswordError == null
    }

    fun register() {
        viewModelScope.launch {
            if (validateForm()) {
                _state.value = RegisterUiState.Loading
                repository.register(_formState.value.email, _formState.value.password)
                    .onSuccess {
                        val uid = repository.getCurrentUserUid()
                        if (uid != null){
                            repository.saveUserProfile(
                                uid = uid,
                                name = _formState.value.name,
                                phone = _formState.value.phone,
                                email = _formState.value.email
                            )
                        }
                        _state.value = RegisterUiState.Success
                    }
                    .onFailure {
                        _state.value = RegisterUiState.Error(it.message ?: "Error")
                    }
            }
        }
    }
}
