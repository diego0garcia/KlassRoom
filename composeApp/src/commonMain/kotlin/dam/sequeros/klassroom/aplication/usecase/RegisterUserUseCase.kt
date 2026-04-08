package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.domain.repository.IAuthRepository

class RegisterUserUseCase(private val repository: IAuthRepository) {
    suspend fun invoke(command: RegisterUserCommand): Result<String> {
        try {
            val user = repository.register(command)
            return Result.success("Woks correct")
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}