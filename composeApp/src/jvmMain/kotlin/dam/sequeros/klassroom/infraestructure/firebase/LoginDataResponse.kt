package dam.sequeros.klassroom.infraestructure.firebase

import kotlinx.serialization.Serializable

@Serializable
data class LoginDataResponse(
    val fields: UserFields
)

@Serializable
data class UserFields(
    val displayName: FirestoreValue? = null,
    val role: FirestoreValue? = null,
    val profilePictureUrl: FirestoreValue? = null
)

@Serializable
data class FirestoreValue(
    val stringValue: String? = null // Firestore usa esta clave para los Strings
)