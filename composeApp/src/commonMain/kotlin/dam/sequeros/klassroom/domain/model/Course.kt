package dam.sequeros.klassroom.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Course (
    val id: String,
    val name: String,
    val subjects: List<Subject>
)