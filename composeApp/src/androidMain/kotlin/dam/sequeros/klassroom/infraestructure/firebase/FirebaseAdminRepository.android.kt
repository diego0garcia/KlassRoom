package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.aplication.command.AddSubjectCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import io.ktor.client.*

actual class FirebaseAdminRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IAdminRepository {
    actual override suspend fun addCourse(command: AddCurseCommand) {
        TODO("Por hacer aun")
    }

    actual override suspend fun addSubject(command: AddSubjectCommand): Boolean {
        return try {
            val subjectData = mapOf(
                "id" to command.id,
                "teacherId" to command.teacherId,
                "name" to command.name,
                "weekDay" to command.weekDay,
                "startHour" to command.startHour,
                "endHour" to command.endHour
            )
            sessionManager.db.collection("subjects").document(command.id).set(subjectData)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}