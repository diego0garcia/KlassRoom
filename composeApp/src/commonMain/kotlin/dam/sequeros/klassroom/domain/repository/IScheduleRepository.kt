package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.domain.model.Subject

interface IScheduleRepository {
    suspend fun getSubjects(command: GetSubjectsCommand): List<Subject>
}