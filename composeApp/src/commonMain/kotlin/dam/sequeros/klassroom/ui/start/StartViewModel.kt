package dam.sequeros.klassroom.ui.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.aplication.usecase.LoginUserUseCase
import dam.sequeros.klassroom.aplication.usecase.RegisterUserUseCase
import dam.sequeros.klassroom.domain.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.copy

class StartViewModel(
    private val sessionManager: SessionManager,
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    fun onUsernameChange(username: String) {
        var usernameError: String? = null

        if (username.isBlank()) usernameError = "Empty username"
        if (" " in username) usernameError = "Username can not contain spaces"

        _state.update {
            it.copy(
                username = username,
                usernameError = usernameError
            )
        }
        validateForm()
    }

    fun onPasswordChange(password: String) {
        var passwordError: String? = null

        if (password.isBlank()) passwordError = "Empty password"
        if (" " in password) passwordError = "Password can not contain spaces"

        _state.update {
            it.copy(
                password = password,
                passwordError = passwordError
            )
        }
        validateForm()
    }

    private fun validateForm() {
        val s = _state.value
        isFormValid.value = s.username.isNotBlank() &&
                s.password.isNotBlank() &&
                s.usernameError == null &&
                s.passwordError == null
        _state.value = state.value.copy(isValid = isFormValid.value)
    }

    fun onLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val loginUserCommand = LoginUserCommand(
                    email = state.value.username,
                    password = state.value.password
                )
                loginUserUseCase.invoke(loginUserCommand)
                    .onSuccess { result ->
                        sessionManager.setCurrentUser(result)
                        _state.update { it.copy(isLoginSuccess = true, isLoading = false) }
                    }
                    .onFailure { error ->
                        _state.update { it.copy(errorMessage = error.message ?: "ERROR UNKNOW") }
                    }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Error login: ${e.message}") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}