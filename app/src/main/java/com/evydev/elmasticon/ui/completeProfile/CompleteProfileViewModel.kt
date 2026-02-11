package com.evydev.elmasticon.ui.completeProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evydev.elmasticon.auth.data.AuthRepository
import com.evydev.elmasticon.ui.register.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class CompleteFormState(

    val name: String = "",
    val phone: String = "",

    val nameError: String? = null,
    val phoneError: String? = null
)

class CompleteProfileViewModel (
    private val repository: AuthRepository = AuthRepository()
): ViewModel(){

    private val _completeFormState = MutableStateFlow(CompleteFormState())
    val completeFormState: StateFlow<CompleteFormState> = _completeFormState

    private val _state = MutableStateFlow<CompleteUiState>(CompleteUiState.Idle)
    val state: StateFlow<CompleteUiState> = _state


    fun onNameChange(newName: String){

        val filteredName = newName.filter { it.isLetter() || it.isWhitespace() }

        val capitalizedName = filteredName.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }

        _completeFormState.value = _completeFormState.value.copy(name = capitalizedName)

    }


    fun onPhoneChange(newPhone: String){

        val filteredPhone = newPhone.filter { it.isDigit()}.take(9)

        _completeFormState.value = _completeFormState.value.copy(phone = filteredPhone)

    }

    fun validateFormComplete(): Boolean{

        val currentName = _completeFormState.value.name
        val currentPhone = _completeFormState.value.phone


        val nameError = if (currentName.isBlank()){
            "El nombre es obligatorio"
        } else {
            null
        }

        val phoneError = if (currentPhone.length < 9){
            "El número debe tener al menos 9 dígitos"
        } else{
            null
        }

      _completeFormState.value = _completeFormState.value.copy(
            nameError = nameError,
            phoneError = phoneError
        )

        return nameError == null && phoneError == null
    }

    fun complete(email: String){

        viewModelScope.launch {
            if (validateFormComplete()){
            _state.value = CompleteUiState.Loading

                val uid = repository.getCurrentUserUid()

                if (uid != null){
                    val result = repository.saveUserProfile(
                        uid = uid,
                        name = _completeFormState.value.name,
                        phone = _completeFormState.value.phone,
                        email = email
                    )

                    result.onSuccess {
                        _state.value = CompleteUiState.Success
                    }.onFailure {
                        _state.value = CompleteUiState.Error(it.message ?: "Error al guardar el perfil")
                    }
                } else{
                    _state.value = CompleteUiState.Error("No se pudo identificar al usuario.")
                }
            }
        }
    }
}