package dam.sequeros.klassroom.domain.repository

import dam.sequeros.klassroom.aplication.command.UpdateProfilePictureCommand

interface IUpdateUserRepository {
    suspend fun updateProfilePicture(command: UpdateProfilePictureCommand)
}