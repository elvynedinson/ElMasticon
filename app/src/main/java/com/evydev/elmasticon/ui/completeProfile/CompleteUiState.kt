package com.evydev.elmasticon.ui.completeProfile

sealed interface CompleteUiState{

    data object Idle: CompleteUiState
    data object Loading: CompleteUiState
    data object Success: CompleteUiState

    data class Error(val message: String): CompleteUiState


}
