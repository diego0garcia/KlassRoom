package dam.sequeros.klassroom.ui.main.admin.addsubject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.AddSubjectCommand
import dam.sequeros.klassroom.aplication.usecase.AddSubjectUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class SubjectViewModel(
    private val addSubjectUseCase: AddSubjectUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SubjectState())
    val state: StateFlow<SubjectState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    fun onNameChange(name: String) {
        var error: String? = null
        if (name.isBlank()) error = "El nombre es obligatorio"
        _state.update { it.copy(name = name, nameError = error) }
        validateForm()
    }

    fun onTeacherIdChange(teacherId: String) {
        var error: String? = null
        if (teacherId.isBlank()) error = "El id del profesor es obligatorio"
        _state.update { it.copy(teacherId = teacherId, teacherIdError = error) }
        validateForm()
    }

    fun onWeekDayChange(weekDay: String) {
        var error: String? = null
        val day = weekDay.toIntOrNull()
        if (weekDay.isBlank()) {
            error = "El día es obligatorio"
        } else if (day == null || day !in 1..5) {
            error = "Día inválido (1-5)"
        }
        _state.update { it.copy(weekDay = weekDay, weekDayError = error) }
        validateForm()
    }

    fun onStartHourChange(startHour: String) {
        var error: String? = null
        if (startHour.isBlank()) error = "La hora de inicio es obligatoria"
        _state.update { it.copy(startHour = startHour, startHourError = error) }
        validateForm()
    }

    fun onEndHourChange(endHour: String) {
        var error: String? = null
        if (endHour.isBlank()) error = "La hora de fin es obligatoria"
        _state.update { it.copy(endHour = endHour, endHourError = error) }
        validateForm()
    }

    private fun validateForm() {
        val s = _state.value
        val valid = s.name.isNotBlank() && s.teacherId.isNotBlank() && s.weekDay.isNotBlank() && s.startHour.isNotBlank() && s.endHour.isNotBlank() &&
            s.nameError == null && s.teacherIdError == null && s.weekDayError == null && s.startHourError == null && s.endHourError == null
        _state.value = s.copy(isValid = valid)
        isFormValid.value = valid
    }

    fun onAddSubject() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isAddedSuccess = false) }
            try {
                val currentState = _state.value
                val command = AddSubjectCommand(
                    id = UUID.randomUUID().toString(),
                    teacherId = currentState.teacherId,
                    name = currentState.name,
                    weekDay = currentState.weekDay.toIntOrNull() ?: 0,
                    startHour = currentState.startHour,
                    endHour = currentState.endHour
                )
                addSubjectUseCase.invoke(command)
                    .onSuccess { _state.update { it.copy(isAddedSuccess = true) } }
                    .onFailure { error ->
                        _state.update { it.copy(errorMessage = error.message ?: "Error al guardar la asignatura") }
                    }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}
