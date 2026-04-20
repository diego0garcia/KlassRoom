package dam.sequeros.klassroom.aplication.command

import kotlinx.serialization.Serializable

@Serializable
data class GetSubjectsCommand(
    val teacherId: String
)