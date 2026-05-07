package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.aplication.command.AddCurseCommand

interface IAdminRepository {
    suspend fun addCourse(command: AddCurseCommand): Boolean
}