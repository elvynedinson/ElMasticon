package com.evydev.elmasticon.ui.forgotPassword

sealed interface ForgotPasswordUiState{

    data object Idle: ForgotPasswordUiState
    data object Loading: ForgotPasswordUiState
    data object Success: ForgotPasswordUiState

    data class Error(val message: String): ForgotPasswordUiState
}