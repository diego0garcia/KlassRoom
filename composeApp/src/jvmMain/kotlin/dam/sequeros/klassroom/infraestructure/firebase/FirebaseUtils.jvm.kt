package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.model.Subject
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

    actual override suspend fun getCourseByTeacher(id: String): List<Course> {
        val subjectList = mutableListOf<Subject>()
        val courseList = mutableListOf<Course>()
        var hasNext: String? = null

        try {
            do {
                val response = client.get(
                    urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/subjects"
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
                    val teacherId = document.fields["teacherId"]?.stringValue
                    if (teacherId == id) {
                        subjectList.add(
                            Subject(
                                id = document.fields["id"]?.stringValue ?: "Not received",
                                name = document.fields["name"]?.stringValue ?: "Not received",
                                endHour = document.fields["endHour"]?.stringValue ?: "Not received",
                                startHour = document.fields["startHour"]?.stringValue ?: "Not received",
                                teacherId = id,
                                curseId = document.fields["curseId"]?.stringValue ?: "Not received",
                                weekDay = document.fields["weekDay"]?.integerValue?.toIntOrNull() ?: -1
                            )
                        )
                    }
                }

                hasNext = data.nextPageToken

            } while (hasNext != null)
        } catch (ex: Exception) {
            throw ex
        }

        try {
            do {
                val response = client.get(
                    urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/courses"
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
                    courseList.add(
                        Course(
                            id = document.fields["id"]?.stringValue ?: "Not received",
                            name = document.fields["name"]?.stringValue ?: "Not received",
                            subjects = subjectList.filter {
                                it.curseId == (document.fields["id"]?.stringValue)
                            },
                        )
                    )
                }

                hasNext = data.nextPageToken

            } while (hasNext != null)
        } catch (ex: Exception) {
            throw ex
        }

        courseList.forEach { course ->
            println(course.name)
            course.subjects.forEach { subject ->
                println(subject.name)
            }
        }

        return courseList
    }
}