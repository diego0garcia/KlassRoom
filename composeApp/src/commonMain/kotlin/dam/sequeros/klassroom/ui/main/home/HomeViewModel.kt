package dam.sequeros.klassroom.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.GetCourseByTeacherCommand
import dam.sequeros.klassroom.aplication.usecase.GetCourseByTeacherUseCase
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    getCourseByTeacherUseCase: GetCourseByTeacherUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _courseList = MutableStateFlow(emptyList<Course>())
    val courseList = _courseList.asStateFlow()

    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse = _selectedCourse.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setSelectedCourse(course: Course) {
        _selectedCourse.update { course }
    }

    init {
        viewModelScope.launch {
            val id = sessionManager.currentUserAccount.value?.id ?: throw Exception("User not logged in")
            val command = GetCourseByTeacherCommand(id = id)
            _isLoading.update { true }
            getCourseByTeacherUseCase.invoke(command)
                .onSuccess { list ->
                    _courseList.update { list }
                    _isLoading.update { false }
                }.onFailure {
                    _isLoading.update { false }
                    throw Exception("Courses cant be retrieved")
                }
        }
    }
}