package dam.sequeros.klassroom.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val id: String,
    val teacherId: String,
    val name: String,
    val weekDay: Int,
    val startHour: String,
    val endHour: String,
)