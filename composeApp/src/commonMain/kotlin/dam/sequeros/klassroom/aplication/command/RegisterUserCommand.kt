package dam.sequeros.klassroom.aplication.command

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserCommand(
    val username: String,
    val email: String,
    val password: String
)