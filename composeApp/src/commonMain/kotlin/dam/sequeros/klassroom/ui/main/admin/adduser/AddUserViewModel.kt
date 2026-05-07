package dam.sequeros.klassroom.ui.main.admin.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.aplication.usecase.RegisterUserUseCase
import dam.sequeros.klassroom.domain.model.users.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _registerUserState = MutableStateFlow(RegisterUserState())
    val registerUserState: StateFlow<RegisterUserState> = _registerUserState.asStateFlow()
    val isRegisterUserFormValid = MutableStateFlow(false)

    fun onUsernameChange(username: String) {
        var usernameError: String? = null
        if (username.isBlank()) usernameError = "Empty username"
        if (" " in username) usernameError = "Username can not contain spaces"
        _registerUserState.update { it.copy(username = username, usernameError = usernameError) }
        validateForm()
    }

    fun onPasswordChange(password: String) {
        var passwordError: String? = null
        if (password.isBlank()) passwordError = "Empty password"
        if (" " in password) passwordError = "Password can not contain spaces"
        _registerUserState.update { it.copy(password = password, passwordError = passwordError) }
        validateForm()
    }

    fun onEmailChange(email: String) {
        var emailError: String? = null
        if (email.isBlank()) emailError = "Empty password"
        if (" " in email) emailError = "Password can not contain spaces"
        _registerUserState.update { it.copy(email = email, emailError = emailError) }
        validateForm()
    }

    fun onRoleChange(role: UserRole) {
        _registerUserState.update { it.copy(role = role) }
    }

    private fun validateForm() {
        val s = _registerUserState.value
        isRegisterUserFormValid.value =
            s.username.isNotBlank() &&
                    s.password.isNotBlank() &&
                    s.email.isNotBlank() &&
                    s.usernameError == null &&
                    s.emailError == null &&
                    s.passwordError == null
        _registerUserState.value = registerUserState.value.copy(isValid = isRegisterUserFormValid.value)
    }

    fun onRegisterUser() {
        viewModelScope.launch {
            _registerUserState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val command = RegisterUserCommand(
                    username = _registerUserState.value.username,
                    email = _registerUserState.value.email,
                    password = _registerUserState.value.password,
                    role = _registerUserState.value.role
                )
                registerUserUseCase.invoke(command)
                    .onSuccess { _registerUserState.update { it.copy(isRegisterSuccess = true) } }
                    .onFailure { error ->
                        _registerUserState.update { it.copy(errorMessage = error.message ?: "UNKNOW ERROR") }
                    }

            } catch (e: Exception) {
                _registerUserState.update { it.copy(errorMessage = "Error register: ${e.message}") }
            } finally {
                _registerUserState.update { it.copy(isLoading = false) }
            }
        }
    }
}