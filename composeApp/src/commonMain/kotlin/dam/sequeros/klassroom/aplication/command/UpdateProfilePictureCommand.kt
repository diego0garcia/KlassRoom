package dam.sequeros.klassroom.aplication.command

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfilePictureCommand(
    val fileName: String,
    val byteArray: ByteArray
)