package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IScheduleRepository
import io.ktor.client.*

actual class FirebaseScheduleRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) :
    IScheduleRepository {
    actual override suspend fun getSubjects(command: GetSubjectsCommand): List<Subject> {
        return try {
            val document = sessionManager.db.collection("subjects").where { "teacherId" equalTo command.teacherId }.get()
            document.documents.map { subject ->
                subject.data<Subject>()
            }
        }catch (e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}