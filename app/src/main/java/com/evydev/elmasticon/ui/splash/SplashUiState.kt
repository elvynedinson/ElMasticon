package com.evydev.elmasticon.ui.splash

sealed class SplashUiState {

    object Loading: SplashUiState()

    object Success: SplashUiState()

    data class Error(val message: String): SplashUiState()

}