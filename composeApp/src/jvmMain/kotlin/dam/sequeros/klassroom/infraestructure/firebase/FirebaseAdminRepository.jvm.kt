package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import dam.sequeros.klassroom.infraestructure.firebase.FirestoreResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.to
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

actual class FirebaseAdminRepository actual constructor(
    val sessionManager: SessionManager,
    val client: HttpClient
) : IAdminRepository {

    actual override suspend fun addCourse(command: AddCurseCommand): Boolean {
        return try {
            val data = mapOf(
                "fields" to mapOf(
                    "name" to mapOf("stringValue" to command.name),
                )
            )

            val petition = client.post(
                urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/courses?documentId=${command.id}"
            ) {
                headers {
                    append("Authorization", "Bearer ${sessionManager.idToken.value}")
                }
                setBody(data)
            }

            if (petition.status.isSuccess()) {
                for (subject in command.list) {
                    addSubject(subject)
                }
            } else {
                throw Exception("ERROR: Adding course failed.")
            }
            true

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    actual override suspend fun getCourses(): List<Course> {
        return try {
            val response: FirestoreResponse = client.get(
                urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/courses"
            ) {
                headers {
                    append("Authorization", "Bearer ${sessionManager.idToken.value}")
                }
            }.body()

            response.documents?.mapNotNull { document ->
                Course(
                    id = document.name.substringAfterLast("/"),
                    name = document.fields["name"]?.stringValue ?: "",
                    subjects = emptyList()
                )
            } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun addSubject(subject: Subject): Boolean {
        return try {
            val firestoreData = mapOf(
                "fields" to mapOf(
                    "curseId" to mapOf("stringValue" to (subject.curseId ?: "")),
                    "teacherId" to mapOf("stringValue" to (subject.teacherId ?: "")),
                    "name" to mapOf("stringValue" to subject.name),
                    "weekDay" to mapOf("integerValue" to subject.weekDay.toString()),
                    "startHour" to mapOf("stringValue" to subject.startHour),
                    "endHour" to mapOf("stringValue" to subject.endHour)
                )
            )
            val petition = client.post(
                urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/subjects?documentId=${Uuid.random()}"
            ) {
                headers {
                    append("Authorization", "Bearer ${sessionManager.idToken.value}")
                }
                setBody(firestoreData)
            }
            petition.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}