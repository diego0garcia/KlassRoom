package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IScheduleRepository

class UpdateProfilePictureUseCase(private val repository: IScheduleRepository) {
    suspend fun invoke(command: GetSubjectsCommand): Result<List<Subject>> {
        try {
            val list = repository.getSubjects(command)
            return if (!list.isEmpty()) Result.success(list) else Result.failure(Exception("LIST IS EMPTY"))
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}