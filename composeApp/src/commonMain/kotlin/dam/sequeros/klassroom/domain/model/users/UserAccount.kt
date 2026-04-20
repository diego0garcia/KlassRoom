package dam.sequeros.klassroom.domain.model.users

import kotlinx.serialization.Serializable

@Serializable
data class UserAccount(
    val id: String?,
    val displayName: String?,
    val email: String?,
    val profilePictureUrl: String?,
    val role: UserRole?, // "user", "teacher", "admin"
)