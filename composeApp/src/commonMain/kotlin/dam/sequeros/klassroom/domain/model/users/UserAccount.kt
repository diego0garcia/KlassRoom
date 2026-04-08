package dam.sequeros.klassroom.domain.model.users

import kotlinx.serialization.Serializable

@Serializable
data class UserAccount(
    val activeProfileId: String?, // ID del perfil seleccionado
    val displayName: String?,
    val email: String?,
    val profilePictureUrl: String?, // URL de la imagen (Google/Apple Auth o Firebase)
    val role: UserRole = UserRole.USER, // "user", "admin", "teacher"
    val banned: Boolean = false, // Bloqueo de acceso
)
