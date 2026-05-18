
package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import io.ktor.client.*

expect class FirebaseAdminRepository(
    sessionManager: SessionManager,
    client: HttpClient
) : IAdminRepository {
    override suspend fun addCourse(command: AddCurseCommand): Boolean
}