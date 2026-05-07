package dam.sequeros.klassroom.aplication.command

import dam.sequeros.klassroom.domain.model.Subject
import kotlinx.serialization.Serializable

@Serializable
data class AddCurseCommand(
    val id: String,
    val name: String,
    val list: List<Subject>
)
