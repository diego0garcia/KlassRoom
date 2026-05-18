package dam.sequeros.klassroom.ui.main.admin.enrollStudient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnrollStudentViewModel(

) : ViewModel() {
    private val _state = MutableStateFlow(EnrollStudentState())
    val state: StateFlow<EnrollStudentState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    fun onDniChange(dni: String) {
        var dniError: String? = null
        if (dni.isBlank()) dniError = "Empty DNI"
        if (" " in dni) dniError = "DNI can not contain spaces"
        _state.update { it.copy(dni = dni, dniError = dniError) }
        validateForm()
    }

    fun onNameChange(name: String) {
        var nameError: String? = null
        if (name.isBlank()) nameError = "Empty student name"
        if (" " in name) nameError = "Name can not contain spaces"
        _state.update { it.copy(name = name, nameError = nameError) }
        validateForm()
    }

    fun onPhoneChange(phone: String) {
        var phoneError: String? = null
        if (phone.isBlank()) phoneError = "Empty phone"
        if (" " in phone) phoneError = "Phone can not contain spaces"
        _state.update { it.copy(phone = phone, phoneError = phoneError) }
        validateForm()
    }

    fun onEmailChange(email: String) {
        var emailError: String? = null
        if (email.isBlank()) emailError = "Empty password"
        if (" " in email) emailError = "Password can not contain spaces"
        _state.update { it.copy(email = email, emailError = emailError) }
        validateForm()
    }

    fun onSubjectIdChange(subjectId: String) {
        var subjectIdError: String? = null
        if (subjectId.isBlank()) subjectIdError = "Empty subject"
        if (" " in subjectId) subjectIdError = "Subject can not contain spaces"
        _state.update { it.copy(curseId = subjectId, curseIdError = subjectIdError) }
        validateForm()
    }

    private fun validateForm() {
        val s = _state.value
        isFormValid.value =
                    s.dni.isNotBlank() &&
                    s.name.isNotBlank() &&
                    s.email.isNotBlank() &&
                    s.phone.isNotBlank() &&
                    s.curseId.isNotBlank() &&
                    s.dniError == null &&
                    s.nameError == null &&
                    s.emailError == null &&
                    s.phoneError == null &&
                    s.curseIdError == null
        _state.value = state.value.copy(isValid = isFormValid.value)
    }

    fun onRegisterUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                /*
                val command = RegisterUserCommand(
                    username = _state.value.name,
                    email = _state.value.email,
                    password = _state.value.dni,
                    role = _state.value.role
                )
                registerUserUseCase.invoke(command)
                    .onSuccess { _registerUserState.update { it.copy(isRegisterSuccess = true) } }
                    .onFailure { error ->
                        _registerUserState.update { it.copy(errorMessage = error.message ?: "UNKNOW ERROR") }
                    }
                */
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error register: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}