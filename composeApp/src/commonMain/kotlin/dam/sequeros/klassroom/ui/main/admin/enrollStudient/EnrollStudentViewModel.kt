package dam.sequeros.klassroom.ui.main.admin.enrollStudient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import dam.sequeros.klassroom.aplication.usecase.GetCoursesUseCase
import dam.sequeros.klassroom.domain.model.Course

class EnrollStudentViewModel(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EnrollStudentState())
    val state: StateFlow<EnrollStudentState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    init {
        loadCourses()
    }

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
        if (email.isBlank()) emailError = "Empty email"
        if (" " in email) emailError = "Email can not contain spaces"
        _state.update { it.copy(email = email, emailError = emailError) }
        validateForm()
    }

    fun onCurseIdChange(curseId: String) {
        var curseIdError: String? = null
        if (curseId.isBlank()) curseIdError = "Empty course"
        _state.update { it.copy(curseId = curseId, curseIdError = curseIdError) }
        validateForm()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            getCoursesUseCase.invoke().onSuccess { courses ->
                _state.update { it.copy(courses = courses) }
            }
        }
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
            _state.update { it.copy(isLoading = true, errorMessage = null, isRegisterSuccess = false) }
            try {
                _state.update {
                    it.copy(
                        isRegisterSuccess = true,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error register: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}