package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.domain.model.users.UserAccount

interface IUtilsRepository {
    suspend fun getTeachers(): List<UserAccount>
}