package com.evydev.elmasticon.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val state : StateFlow<SplashUiState> = _state

    init {
        startSplash()
    }

    private fun startSplash() {

        viewModelScope.launch {

            try {
                _state.value = SplashUiState.Success

            }catch (e: Exception){
                _state.value = SplashUiState.Error("Error al cargar...")
            }

        }
    }
}