package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.domain.model.users.UserAccount

interface IUtilsRepository {
    suspend fun getTeachers(): List<UserAccount>
    suspend fun getCourseByTeacher(id: String): List<Course>
}