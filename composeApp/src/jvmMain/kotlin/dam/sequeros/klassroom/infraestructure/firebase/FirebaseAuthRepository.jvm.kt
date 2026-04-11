package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.domain.repository.IAuthRepository
import dam.sequeros.klassroom.infraestructure.TokenJwt
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess

actual class FirebaseAuthRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IAuthRepository {

    actual override suspend fun login(command: LoginUserCommand): UserAccount? {

        //PRIMERO EL LOGIN EN AUTH
        val authRequest = client.post(
            urlString = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${DesktopFirebaseConfig.apiKey}"
        ) {
            setBody(command)
        }

        if (authRequest.status.value == 401) {
            throw Exception("Email o contraseña incorrectos")
        }

        if (!authRequest.status.isSuccess()) {
            throw Exception("Error del servidor: ${authRequest.status.value}")
        }

        //PEDIMOS LOS DATOS DEL USUARIO AHORA
        val authResponse: LoginAuthResponse = authRequest.body()
        val dataRequest = client.get(
            urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/users/${authResponse.localId}"
        ) {
            headers.append("Authorization", "Bearer ${authResponse.idToken}")
        }

        if (dataRequest.status.isSuccess()) {
            val data: LoginDataResponse = dataRequest.body()
            val user = UserAccount(
                id = authResponse.localId,
                displayName = data.fields.displayName?.stringValue ?: "Sin nombre",
                email = authResponse.email ?: "johndoe@email.com",
                profilePictureUrl = data.fields.profilePictureUrl?.stringValue,
                role = UserRole.valueOf(data.fields.role?.stringValue ?: UserRole.TEACHER.name)
            )
            sessionManager.logIn(user, authResponse.idToken, authResponse.refreshToken)
            return user
        }
        return null
    }

    actual override suspend fun register(command: RegisterUserCommand) {/*TODO: NO HACIDO*/
    }

    actual override suspend fun update() {/*TODO: NO HACIDO*/
    }
}