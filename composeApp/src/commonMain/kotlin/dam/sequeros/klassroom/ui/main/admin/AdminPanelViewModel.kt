package dam.sequeros.klassroom.ui.main.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.aplication.usecase.RegisterUserUseCase
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.ui.main.admin.adduser.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminPanelViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    fun onUsernameChange(username: String) {
        var usernameError: String? = null
        if (username.isBlank()) usernameError = "Empty username"
        if (" " in username) usernameError = "Username can not contain spaces"
        _state.update { it.copy(username = username, usernameError = usernameError) }
        validateForm()
    }

    fun onPasswordChange(password: String) {
        var passwordError: String? = null
        if (password.isBlank()) passwordError = "Empty password"
        if (" " in password) passwordError = "Password can not contain spaces"
        _state.update { it.copy(password = password, passwordError = passwordError) }
        validateForm()
    }

    fun onEmailChange(email: String) {
        var emailError: String? = null
        if (email.isBlank()) emailError = "Empty password"
        if (" " in email) emailError = "Password can not contain spaces"
        _state.update { it.copy(email = email, emailError = emailError) }
        validateForm()
    }

    fun onRoleChange(role: UserRole) {
        _state.update { it.copy(role = role) }
    }

    private fun validateForm() {
        val s = _state.value
        isFormValid.value =
            s.username.isNotBlank() &&
            s.password.isNotBlank() &&
            s.email.isNotBlank() &&
            s.usernameError == null &&
            s.emailError == null &&
            s.passwordError == null
        _state.value = state.value.copy(isValid = isFormValid.value)
    }

    fun onRegisterUser() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val command = RegisterUserCommand(
                    username = _state.value.username,
                    email = _state.value.email,
                    password = _state.value.password,
                    role = _state.value.role
                )
                registerUserUseCase.invoke(command)
                    .onSuccess { _state.update { it.copy(isRegisterSuccess = true) } }
                    .onFailure { error ->
                        _state.update { it.copy(errorMessage = error.message ?: "UNKNOW ERROR") }
                    }

            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error register: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}