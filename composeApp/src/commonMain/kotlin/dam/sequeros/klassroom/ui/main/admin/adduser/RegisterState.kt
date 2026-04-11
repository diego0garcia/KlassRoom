package dam.sequeros.klassroom.ui.main.admin.adduser

import dam.sequeros.klassroom.domain.model.users.UserRole

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val role: UserRole = UserRole.USER,

    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val isValid:Boolean = false,

    val usernameError: String? = null,
    val passwordError: String? = null,
    val emailError: String? = null,

    val errorMessage: String? = null
)
