package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.repository.IUtilsRepository

class GetAllTeachersUseCase(private val repository: IUtilsRepository) {
    suspend fun invoke(): Result<List<UserAccount>> {
        try {
            val list = repository.getTeachers()
            return if (!list.isEmpty()) Result.success(list) else Result.failure(Exception("LIST IS EMPTY"))
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}