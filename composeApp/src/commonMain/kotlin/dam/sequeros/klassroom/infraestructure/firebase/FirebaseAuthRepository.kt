
package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.repository.IAuthRepository
import io.ktor.client.HttpClient

expect class FirebaseAuthRepository(
    sessionManager: SessionManager,
    client: HttpClient
) : IAuthRepository {
    override suspend fun login(command: LoginUserCommand): UserAccount?
    override suspend fun register(command: RegisterUserCommand)
    override suspend fun update()
}