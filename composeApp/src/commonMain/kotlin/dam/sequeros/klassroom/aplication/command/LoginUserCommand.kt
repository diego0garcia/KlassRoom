package dam.sequeros.klassroom.aplication.command

import kotlinx.serialization.Serializable

@Serializable
data class LoginUserCommand(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)