package dam.sequeros.klassroom.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Student (
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val dni: String,
)