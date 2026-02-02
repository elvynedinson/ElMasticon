package com.evydev.elmasticon.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel() : ViewModel() {

    private val _state = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val state : StateFlow<SplashUiState> = _state

    init {
        startSplash()
    }

    private fun startSplash() {

        viewModelScope.launch {

            try {
                delay(5000)
                _state.value = SplashUiState.Success

            }catch (e: Exception){
                _state.value = SplashUiState.Error("Error al cargar...")
            }

        }
    }
}