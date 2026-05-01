package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.aplication.command.AddSubjectCommand
import dam.sequeros.klassroom.domain.repository.IScheduleRepository

class AddSubjectUseCase(private val repository: IScheduleRepository) {
    suspend fun invoke(command: AddSubjectCommand): Result<Boolean> {
        return try {
            val result = repository.addSubject(command)
            if (result) {
                Result.success(true)
            } else {
                Result.failure(Exception("Error al guardar la asignatura"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
