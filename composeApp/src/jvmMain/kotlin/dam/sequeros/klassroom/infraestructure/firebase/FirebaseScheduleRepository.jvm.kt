package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IScheduleRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

actual class FirebaseScheduleRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IScheduleRepository {

    actual override suspend fun getSubjects(command: GetSubjectsCommand): List<Subject> {
        return try {
            val url =
                "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents:runQuery"

            //ESTO E COMO CREAR UN SQL PERO CON OBJETOS PA METERLE AHI Y FILTRAR POR EL ID DEL PROFESOR
            //PARA NO HACER 20k CONSULTAS Y PEDRO NO SE ENFADE
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

            //LE METEMOS CON TO AHI Y VEMOS QUE NOS RESPONDE
            val response: List<FirestoreQueryResponse> = client.post(url) {
                setBody(queryRequest)
                // header("Authorization", "Bearer ${sessionManager.idToken}")
            }.body()

            //PASAMOS EL FORMATO CRIMINAL ESE DEL FIRESTORE A CRISTIANO
            //LOS OBJETOS DE STRING VALUE Y ESO ESTAN EN FIRESTOREOBJECTS.KT
            //SI NECESITAS MAS HAZLOS CON CHATGPT NI T RAYES
            response.mapNotNull { item ->
                item.document?.let { doc ->
                    Subject(
                        id = doc.name.substringAfterLast("/"),
                        name = doc.fields["name"]?.stringValue ?: "",
                        teacherId = doc.fields["teacherId"]?.stringValue ?: "",
                        startHour = doc.fields["startHour"]?.stringValue ?: "",
                        endHour = doc.fields["endHour"]?.stringValue ?: "",
                        weekDay = doc.fields["weekDay"]?.integerValue?.toIntOrNull() ?: 0
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}