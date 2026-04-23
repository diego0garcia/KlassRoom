package dam.sequeros.klassroom.ui.main.schedules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.aplication.usecase.GetSubjectsUseCase
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val sessionManager: SessionManager,
    private val getSubjectsUseCase: GetSubjectsUseCase,
) : ViewModel() {

    private val currentUser = sessionManager.currentUserAccount
    private val _subjects = MutableStateFlow<List<Subject>>(emptyList())

    init {
        viewModelScope.launch {
            try {
                getSubjectsUseCase.invoke(GetSubjectsCommand(currentUser.value?.id!!)).onSuccess { result ->
                    _subjects.update { result }
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val subjects = _subjects.asStateFlow()
}