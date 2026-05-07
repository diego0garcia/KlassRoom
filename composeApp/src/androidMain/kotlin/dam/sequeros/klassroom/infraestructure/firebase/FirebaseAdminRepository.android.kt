package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import io.ktor.client.HttpClient

actual class FirebaseAdminRepository actual constructor(
    sessionManager: SessionManager,
    client: HttpClient
) : IAdminRepository {
    actual override suspend fun addCourse(command: AddCurseCommand) {
    }
}