package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.aplication.command.GetCourseByTeacherCommand
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.repository.IUtilsRepository

class GetCourseByTeacherUseCase(private val repository: IUtilsRepository) {
    suspend fun invoke(command: GetCourseByTeacherCommand): Result<List<Course>> {
        try {
            val list = repository.getCourseByTeacher(command.id)
            return if (!list.isEmpty()) Result.success(list) else Result.failure(Exception("COURSE LIST IS EMPTY"))
        }catch (ex: Exception){
            return Result.failure(ex)
        }
    }
}