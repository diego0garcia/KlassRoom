package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.UpdateProfilePictureCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IUpdateUserRepository
import io.ktor.client.*

expect class FirebaseUpdateUserRepository(
    sessionManager: SessionManager,
    client: HttpClient
) : IUpdateUserRepository {
    override suspend fun updateProfilePicture(command: UpdateProfilePictureCommand)
}