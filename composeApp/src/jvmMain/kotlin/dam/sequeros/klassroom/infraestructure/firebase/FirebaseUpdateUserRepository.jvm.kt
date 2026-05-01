package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.UpdateProfilePictureCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IUpdateUserRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.server.util.url

actual class FirebaseUpdateUserRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IUpdateUserRepository {
    actual override suspend fun updateProfilePicture(command: UpdateProfilePictureCommand) {

    }
}