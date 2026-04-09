package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.domain.model.users.UserAccount
import io.ktor.http.ContentType

interface IAuthRepository {
    suspend fun register(command: RegisterUserCommand)
    suspend fun login(command: LoginUserCommand) : UserAccount?
    suspend fun update()
}