package dam.sequeros.klassroom.infraestructure.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("id_token")
    val idToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("expires_in")
    val expiresIn: Int,

    @SerialName("token_type")
    val tokenType: String
)
