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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess

actual class FirebaseAuthRepository actual constructor(
    private val sessionManager: SessionManager,
    private val client: HttpClient
) : IAuthRepository {

    actual override suspend fun login(command: LoginUserCommand): UserAccount? {

        val request = client.post(
            urlString = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${DesktopFirebaseConfig.apiKey}") {
            setBody(command)
        }

        if(request.status.value == 401){
            throw Exception("Email o contraseña incorrectos")
        }

        if (!request.status.isSuccess()) {
            throw Exception("Error del servidor: ${request.status.value}")
        }

        val respuesta: LoginResponse = request.body()
        val tokenDatos = TokenJwt(respuesta.idToken)

        val user = UserAccount(
            id = tokenDatos.payload.id ?: "",
            displayName =  tokenDatos.payload.displayName ?: "",
            email = tokenDatos.payload.email ?: "",
            profilePictureUrl = tokenDatos.payload.profilePictureUrl,
            role = UserRole.valueOf(tokenDatos.payload.role ?: UserRole.TEACHER.name) ,
        )

        sessionManager.logIn(user,respuesta.idToken, respuesta.refreshToken)

        return user
    }

    actual override suspend fun register(command: RegisterUserCommand) {/*TODO: NO HACIDO*/}
    actual override suspend fun update() {/*TODO: NO HACIDO*/}
}