package dam.sequeros.klassroom.ui.main.admin.addcurse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.aplication.usecase.AddCurseUseCase
import dam.sequeros.klassroom.aplication.usecase.GetAllTeachersUseCase
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.ui.main.admin.addsubject.SubjectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CurseViewModel(
    private val addCurseUseCase: AddCurseUseCase,
    private val getAllTeachersUseCase: GetAllTeachersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CurseState())
    val state: StateFlow<CurseState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    private val _subjectState = MutableStateFlow(SubjectState())
    val subjectState: StateFlow<SubjectState> = _subjectState.asStateFlow()
    val isSubjectFormValid = MutableStateFlow(false)

    private val _teacherList = MutableStateFlow(emptyList<UserAccount>())
    val teacherList: StateFlow<List<UserAccount>> = _teacherList.asStateFlow()

    init {
        loadTeachers()
    }

    private fun loadTeachers() {
        viewModelScope.launch {
            try {
                val result = getAllTeachersUseCase.invoke().onSuccess { teachers ->
                    _teacherList.update { teachers }
                }.onFailure { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error: ${result.message}",
                            isAddedSuccess = true
                        )
                    }
                }

            } catch (e: Exception) {
                print("ERROR: Curse has not been added.")
                e.printStackTrace()
            }
        }
    }

    fun onNameChange(name: String) {
        var error: String? = null
        if (name.isBlank()) error = "El nombre es obligatorio"
        _state.update { it.copy(name = name, nameError = error) }
        validateForm()
    }

    fun addSubject(subject: Subject) {
        print(subject.toString())
        _state.update { it.copy(subjects = it.subjects.filter { inSubject -> inSubject.id != subject.id } + subject) }
    }

    fun onDeleteSubject(subject: Subject) {
        _state.update { it.copy(subjects = it.subjects.filter { it != subject }) }
    }

    fun editSubject(subject: Subject) {
        _state.update { it.copy(subjects = it.subjects.filter { it.id != subject.id } + subject) }
    }

    private fun validateForm() {
        val s = _state.value
        val valid = s.name.isNotBlank() && s.nameError == null
        _state.value = s.copy(isValid = valid)
        isFormValid.value = valid
    }

    fun onClearSubjectForm() {
        _subjectState.update { SubjectState() }
    }

    fun onEditForm(subject: Subject) {
        _subjectState.update {
            SubjectState(
                name = subject.name,
                teacherId = subject.teacherId ?: "",
                weekDay = subject.weekDay.toString(),
                startHour = subject.startHour,
                endHour = subject.endHour,
            )
        }
    }

    fun onSubjectNameChange(name: String) {
        var error: String? = null
        if (name.isBlank()) error = "El nombre es obligatorio"
        _subjectState.update { it.copy(name = name, nameError = error) }
        validateSubjectForm()
    }

    fun onSubjectTeacherIdChange(teacherId: String) {
        var error: String? = null
        if (teacherId.isBlank()) error = "El id del profesor es obligatorio"
        _subjectState.update { it.copy(teacherId = teacherId, teacherIdError = error) }
        validateSubjectForm()
    }

    fun onSubjectWeekDayChange(weekDay: String) {
        var error: String? = null
        val day = weekDay.toIntOrNull()
        if (weekDay.isBlank()) {
            error = "El día es obligatorio"
        } else if (day == null || day !in 1..5) {
            error = "Día inválido (1-5)"
        }
        _subjectState.update { it.copy(weekDay = weekDay, weekDayError = error) }
        validateSubjectForm()
    }

    fun onSubjectStartHourChange(startHour: String) {
        var error: String? = null
        if (startHour.isBlank()) error = "La hora de inicio es obligatoria"
        try {
            if (_subjectState.value.endHour != "") {
                if (startHour.toInt() >= _subjectState.value.endHour.toInt()) error = "La hora de inicio no puede ser mayor ni igual a la final"
            }
        } catch (e: Exception) {
            error = "Eso no es un numero"
        }
        _subjectState.update { it.copy(startHour = startHour, startHourError = error) }
        validateSubjectForm()
    }

    fun onSubjectEndHourChange(endHour: String) {
        var error: String? = null
        if (endHour.isBlank()) error = "La hora de fin es obligatoria"
        try {
            if (_subjectState.value.startHour != "") {
                if (endHour.toInt() <= _subjectState.value.startHour.toInt()) error = "La hora de fin no puede ser menor ni igual a la inicial"
            }
        } catch (e: Exception) {
            error = "Eso no es un numero"
        }
        _subjectState.update { it.copy(endHour = endHour, endHourError = error) }
        validateSubjectForm()
    }

    private fun validateSubjectForm() {
        val s = _subjectState.value
        val valid =
            s.name.isNotBlank() && s.teacherId.isNotBlank() && s.weekDay.isNotBlank() && s.startHour.isNotBlank() && s.endHour.isNotBlank() &&
                    s.nameError == null && s.teacherIdError == null && s.weekDayError == null && s.startHourError == null && s.endHourError == null
        _subjectState.value = s.copy(isValid = valid)
        isSubjectFormValid.value = valid
    }

    fun onAddSubject() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isAddedSuccess = false) }
            try {
                val subject = Subject(
                    id = Uuid.generateV7().toString(),
                    teacherId = subjectState.value.teacherId,
                    curseId = state.value.id,
                    name = subjectState.value.name,
                    weekDay = subjectState.value.weekDay.toInt(),
                    startHour = subjectState.value.startHour,
                    endHour = subjectState.value.endHour
                )

                addSubject(subject)
                onClearSubjectForm()
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    //FUNCION PRINCIPAL DE EDITAR COGE EL INIDICE DE LA SUBJECT A EDITAR
    //Y LUEGO LLAMAMOS A LA FUNCION DE AÑADIR QUE LA FILTRARA PARA QUITAR LA VIEJA Y AÑADIRÁ
    //LA NUEVA, LUEGO LIMPIAMOS EL FORMULARIO - Diego 03/05/2026
    fun onEditSubject(index: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isAddedSuccess = false) }
            try {
                val updatedSubject = _state.value.subjects[index].copy(
                    teacherId = subjectState.value.teacherId,
                    name = subjectState.value.name,
                    weekDay = subjectState.value.weekDay.toInt(),
                    startHour = subjectState.value.startHour,
                    endHour = subjectState.value.endHour
                )
                addSubject(updatedSubject)
                onClearSubjectForm()

            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAddCurse() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isAddedSuccess = false) }
            try {
                val command = AddCurseCommand(
                    id = _state.value.id,
                    name = _state.value.name,
                    list = _state.value.subjects
                )

                val result = addCurseUseCase.invoke(command).onSuccess {
                    _state.update {
                        it.copy(
                            id = Uuid.random().toString(),
                            isLoading = false,
                            errorMessage = null,
                            isAddedSuccess = true
                        )
                    }
                }.onFailure { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error: ${result.message}",
                            isAddedSuccess = true
                        )
                    }
                }

            } catch (e: Exception) {
                print("ERROR: Curse has not been added.")
                e.printStackTrace()
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message}",
                        isAddedSuccess = true
                    )
                }
            }
        }
    }
}
