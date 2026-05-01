package dam.sequeros.klassroom.aplication.command

import kotlinx.serialization.Serializable

@Serializable
data class AddSubjectCommand(
    val id: String,
    val teacherId: String,
    val name: String,
    val weekDay: Int,
    val startHour: String,
    val endHour: String
)
