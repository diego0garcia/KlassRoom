package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.domain.repository.IUtilsRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import java.util.Collections.emptyList

actual class FirebaseUtilsRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IUtilsRepository {
    actual override suspend fun getTeachers(): List<UserAccount> {

        val list = mutableListOf<UserAccount>()
        var hasNext: String? = null

        try {
            do {
                val response = client.get(
                    urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/users"
                ) {
                    headers {
                        append("Authorization", "Bearer ${sessionManager.idToken.value}")
                    }
                    url {
                        parameter("pageSize", "300")
                        if (hasNext != null) {
                            parameter("pageToken", hasNext)
                        }
                    }
                }

                val data = response.body<FirestoreResponse>()

                data.documents?.forEach { document ->
                    val role = document.fields["role"]?.stringValue
                    if (role == UserRole.TEACHER.name) {
                        list.add(
                            UserAccount(
                                id = document.fields["activeProfileId"]?.stringValue ?: "Not received",
                                displayName = document.fields["displayName"]?.stringValue ?: "Not received",
                                email = document.fields["email"]?.stringValue ?: "Not received",
                                profilePictureUrl = document.fields["profilePictureUrl"]?.stringValue ?: "Not received",
                                role = UserRole.TEACHER,
                            )
                        )
                    }
                }

                hasNext = data.nextPageToken

            } while (hasNext != null)
        } catch (ex: Exception) {
            throw ex
        }
        return list
    }
}