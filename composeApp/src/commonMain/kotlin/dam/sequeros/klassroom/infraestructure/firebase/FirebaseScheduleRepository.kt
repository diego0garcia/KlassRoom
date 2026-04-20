package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.GetSubjectsCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.domain.repository.IScheduleRepository
import io.ktor.client.HttpClient

expect class FirebaseScheduleRepository(
    sessionManager: SessionManager,
    client: HttpClient
) : IScheduleRepository {
    override suspend fun getSubjects(command: GetSubjectsCommand): List<Subject>
}