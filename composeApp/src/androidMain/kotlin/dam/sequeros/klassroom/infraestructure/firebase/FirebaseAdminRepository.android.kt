package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.aplication.command.AddSubjectCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import io.ktor.client.*

actual class FirebaseAdminRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IAdminRepository {
    actual override suspend fun addCourse(command: AddCurseCommand): Boolean {
        return false
    }

    actual override suspend fun getCourses(): List<Course> {
        return emptyList()
    actual override suspend fun addCourse(command: AddCurseCommand): Boolean {
        return try {
            val courseData = mapOf(
                "fields" to mapOf(
                    "id" to mapOf("stringValue" to command.id),
                    "name" to mapOf("stringValue" to command.name),
                )
            )

            sessionManager.db.collection("courses").document(command.id).set(courseData)

            for (subject in command.list){
                addSubject(
                    AddSubjectCommand(
                        subject.id,
                        subject.teacherId,
                        subject.name,
                        subject.weekDay,
                        subject.startHour,
                        subject.endHour
                    )
                )
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

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