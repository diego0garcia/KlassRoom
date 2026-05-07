package dam.sequeros.klassroom.domain.model.enrollment

import kotlinx.serialization.Serializable

@Serializable
data class Enrollment (
    val id: String,
    val curseId: String,
    val studentId: String,
    val enrollmentData: String,
    val enrollmentState: EnrollmentState,
)