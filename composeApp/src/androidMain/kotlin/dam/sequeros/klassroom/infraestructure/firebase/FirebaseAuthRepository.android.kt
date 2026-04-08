package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.repository.IAuthRepository
import kotlin.text.get

actual class FirebaseAuthRepository actual constructor(
    private val sessionManager: SessionManager
) : IAuthRepository {
    actual override suspend fun login(command: LoginUserCommand): UserAccount? {
        return try {
            val authResult = sessionManager.auth.signInWithEmailAndPassword(
                email = command.email,
                password = command.password
            )

            val uuid = authResult.user?.uid

            if (uuid != null) {
                val document = sessionManager.db.collection("users").document(uuid).get()
                document.data<UserAccount>()
            } else {
                null
            }
        } catch (e: Exception) {
            println("AUTH ERROR: ${e.message}")
            null
        }
    }

    actual override suspend fun register(command: RegisterUserCommand) {

        val check = sessionManager.auth.createUserWithEmailAndPassword(
            email = command.email,
            password = command.password
        )

        val uuid = check.user?.uid

        if (uuid != null) {
            try {
                val userToInsert = UserAccount(
                    activeProfileId = uuid,
                    displayName = command.username,
                    email = command.email,
                    profilePictureUrl = null,
                )

                sessionManager.db.collection("users").document(uuid).set(userToInsert)

                println("INFO: Firestore guardado correctamente para $uuid")

            } catch (e: Exception) {
                println("ERROR FIRESTORE: ${e.message}")
                e.printStackTrace()
                throw e
            }
        }
    }

    actual override suspend fun update() {

    }
}