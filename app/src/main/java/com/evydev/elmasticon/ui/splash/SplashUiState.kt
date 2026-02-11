package com.evydev.elmasticon.ui.splash

sealed interface SplashUiState {

    data object Loading: SplashUiState
    data object Success: SplashUiState

    data class Error(val message: String): SplashUiState

}