package dam.sequeros.klassroom.aplication.usecase

import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.repository.IAdminRepository

class GetCoursesUseCase(private val repository: IAdminRepository) {
    suspend fun invoke(): Result<List<Course>> {
        return try {
            Result.success(repository.getCourses())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}
