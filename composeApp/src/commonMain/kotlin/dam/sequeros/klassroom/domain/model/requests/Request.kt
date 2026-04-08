package dam.sequeros.klassroom.domain.model.requests

import kotlinx.serialization.Serializable



@Serializable
data class Request (
    val requestId: String,
    val requesterId: String,
    val requestEmail: String,
    val requestState: RequestState,
    val requesterMessage: String?,
    val adminMessage: String?,
)