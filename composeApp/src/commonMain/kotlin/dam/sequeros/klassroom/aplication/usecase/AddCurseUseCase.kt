package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.aplication.command.AddSubjectCommand
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import dam.sequeros.klassroom.domain.repository.IScheduleRepository

class AddCurseUseCase(private val repository: IAdminRepository) {
    suspend fun invoke(command: AddCurseCommand): Result<Boolean> {
        return try {
            val result = repository.addCourse(command)
            if (result) {
                Result.success(true)
            } else {
                Result.failure(Exception("Error al guardar el curso"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
