package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.UpdateProfilePictureCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IUpdateUserRepository
import io.ktor.client.HttpClient

actual class FirebaseUpdateUserRepository actual constructor(
    sessionManager: SessionManager,
    client: HttpClient
) : IUpdateUserRepository {
    actual override suspend fun updateProfilePicture(command: UpdateProfilePictureCommand) {
    }
}