package com.evydev.elmasticon.ui.register

sealed class RegisterUiState{

    object Idle: RegisterUiState()
    object Loading: RegisterUiState()
    object Success: RegisterUiState()

    data class Error(val message: String): RegisterUiState()

}