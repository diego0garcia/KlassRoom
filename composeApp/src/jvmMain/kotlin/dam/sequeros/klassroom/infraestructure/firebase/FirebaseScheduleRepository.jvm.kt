package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.AddSubjectCommand
import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IScheduleRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

actual class FirebaseScheduleRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IScheduleRepository {

    actual override suspend fun getSubjects(command: GetSubjectsCommand): List<Subject> {
        return try {
            val url = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents:runQuery"

            val queryRequest = RunQueryRequest(
                structuredQuery = StructuredQuery(
                    from = listOf(CollectionSelector(collectionId = "subjects")),
                    where = Filter(
                        fieldFilter = FieldFilter(
                            field = FieldReference(fieldPath = "teacherId"),
                            op = "EQUAL",
                            value = FirestoreValue(stringValue = command.teacherId)
                        )
                    )
                )
            )

            val response: List<FirestoreQueryResponse> = client.post(url) {
                setBody(queryRequest)
                //header("Authorization", "Bearer ${sessionManager.idToken}")
            }.body()

            response.mapNotNull { item ->
                item.document?.let { data ->
                    Subject(
                        id = data.name.substringAfterLast("/"),
                        name = data.fields["name"]?.stringValue ?: "",
                        teacherId = data.fields["teacherId"]?.stringValue ?: "",
                        startHour = data.fields["startHour"]?.stringValue ?: "",
                        endHour = data.fields["endHour"]?.stringValue ?: "",
                        weekDay = data.fields["weekDay"]?.integerValue?.toIntOrNull() ?: 0
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    actual override suspend fun addSubject(command: AddSubjectCommand): Boolean {
        return try {
            val firestoreData = mapOf(
                "fields" to mapOf(
                    "teacherId" to mapOf("stringValue" to command.teacherId),
                    "name" to mapOf("stringValue" to command.name),
                    "weekDay" to mapOf("integerValue" to command.weekDay.toString()),
                    "startHour" to mapOf("stringValue" to command.startHour),
                    "endHour" to mapOf("stringValue" to command.endHour)
                )
            )
            val dataRequest = client.post(
                urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/subjects?documentId=${command.id}"
            ) {
                headers {
                    append("Authorization", "Bearer ${sessionManager.idToken.value}")
                }
                setBody(firestoreData)
            }
            dataRequest.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}