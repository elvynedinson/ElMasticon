package com.evydev.elmasticon.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evydev.elmasticon.auth.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(

    private val repository: AuthRepository = AuthRepository()

): ViewModel() {

    private val _state = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val state: StateFlow<LoginUiState> = _state


    fun login(email: String, password: String){

        viewModelScope.launch {

            _state.value = LoginUiState.Loading

            val result = repository.login(email, password)

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