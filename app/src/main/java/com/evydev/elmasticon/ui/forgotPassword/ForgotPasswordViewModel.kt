package com.evydev.elmasticon.ui.forgotPassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evydev.elmasticon.auth.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotFormState(
    val email: String = "",
    val emailError: String? = null
)

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // Estados UiState
    private val _forgotUiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val forgotUiState: StateFlow<ForgotPasswordUiState> = _forgotUiState

    // Estado Formulary

    private val _forgotFormState = MutableStateFlow(ForgotFormState())
    val forgotFormState: StateFlow<ForgotFormState> = _forgotFormState

    // Copy
    fun onChangeEmail(newEmail: String) {

        val filtered = filterSecurityText(newEmail).replace(" ", "")

        _forgotFormState.value = _forgotFormState.value.copy(email = filtered)


    }

    // Validate Forgot
    fun validateFormForgot(): Boolean {

        val currentEmail = _forgotFormState.value.email
        val emailPattern = Patterns.EMAIL_ADDRESS

        val emailError = when {
            currentEmail.isBlank() -> "El correo es obligatorio"
                !emailPattern.matcher(currentEmail).matches() -> "Por favor, ingresa un correo válido"
            else -> null
        }

        _forgotFormState.value = _forgotFormState.value.copy(emailError = emailError)

        return emailError == null

    }

    // Security Text
    fun filterSecurityText(text: String): String {
        return text.filter { char ->
            char.category != CharCategory.OTHER_SYMBOL && char.category != CharCategory.SURROGATE
        }
    }

    fun sendPasswordReset(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (validateFormForgot()) {

                _forgotUiState.value = ForgotPasswordUiState.Loading

                repository.sendPasswordResetEmail(_forgotFormState.value.email)
                    .onSuccess {
                        _forgotUiState.value = ForgotPasswordUiState.Success
                        onSuccess()
                    }
                    .onFailure {
                        _forgotUiState.value =
                            ForgotPasswordUiState.Error(it.message ?: "Error desconocido")
                    }
            }
        }
    }


}
