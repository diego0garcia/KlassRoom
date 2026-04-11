package dam.sequeros.klassroom.aplication.command

import dam.sequeros.klassroom.domain.model.users.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserCommand(
    val username: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val returnSecureToken: Boolean = true
    )