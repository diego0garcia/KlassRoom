package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.repository.IAuthRepository

class LoginUserUseCase(private val repository: IAuthRepository) {
    suspend fun invoke(command: LoginUserCommand): Result<UserAccount> {
        try {
            val user = repository.login(command)
            return if (user != null) Result.success(user) else Result.failure(Exception("USER IS NULL"))
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}