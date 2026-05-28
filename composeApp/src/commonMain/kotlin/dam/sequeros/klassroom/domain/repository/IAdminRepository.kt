package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.aplication.command.AddCurseCommand
import dam.sequeros.klassroom.domain.model.Course

interface IAdminRepository {
    suspend fun addCourse(command: AddCurseCommand): Boolean
    suspend fun getCourses(): List<Course>
import dam.sequeros.klassroom.aplication.command.AddSubjectCommand

interface IAdminRepository {
    suspend fun addCourse(command: AddCurseCommand): Boolean
    suspend fun addSubject(command: AddSubjectCommand): Boolean
}