package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.repository.IUtilsRepository
import io.ktor.client.HttpClient

actual class FirebaseUtilsRepository actual constructor(
    sessionManager: SessionManager,
    client: HttpClient
) : IUtilsRepository {
    actual override suspend fun getTeachers(): List<UserAccount> {
        TODO("Not yet implemented")
    }
}