package dam.sequeros.klassroom.ui.start

data class LoginState(
    // Datos de inicio de sesión rápidos
    val username: String = "paco@paco.es",
    val password: String = "Ab123456789*",

    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val isValid:Boolean = false,

    val usernameError: String? = null,
    val passwordError: String? = null,

    val errorMessage: String? = null
)
