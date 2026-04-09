package dam.sequeros.klassroom.infraestructure.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("localId")
    val localId: String,
    @SerialName("idToken")
    val idToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    val email: String? = null,
    val displayName: String? = null
)
