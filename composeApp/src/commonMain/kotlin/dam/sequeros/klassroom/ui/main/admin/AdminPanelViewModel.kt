package dam.sequeros.klassroom.ui.main.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.aplication.usecase.RegisterUserUseCase
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.ui.main.admin.adduser.RegisterUserState
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.EnrollStudentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminPanelViewModel(

) : ViewModel() {

}