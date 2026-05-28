package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.repository.IUtilsRepository
import io.ktor.client.HttpClient

expect class FirebaseUtilsRepository(
    sessionManager: SessionManager,
    client: HttpClient
) : IUtilsRepository {
    override suspend fun getTeachers(): List<UserAccount>
    override suspend fun getCourseByTeacher(id: String): List<Course>
}