package dam.sequeros.klassroom.infraestructure.firebase

import dam.sequeros.klassroom.aplication.command.LoginUserCommand
import dam.sequeros.klassroom.aplication.command.RegisterUserCommand
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.domain.repository.IAuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

actual class FirebaseAuthRepository actual constructor(
    private val sessionManager: SessionManager
) : IAuthRepository {

    private val httpClient = HttpClient(OkHttp) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }

    actual override suspend fun login(command: LoginUserCommand): UserAccount? {
        val authResponse = httpClient.post(firebaseAuthUrl("signInWithPassword")) {
            setBody(
                FirebaseSignInRequest(
                    email = command.email,
                    password = command.password
                )
            )
        }.body<FirebaseAuthResponse>()

        val user = fetchUserAccount(
            uid = authResponse.localId,
            idToken = authResponse.idToken
        ) ?: UserAccount(
            activeProfileId = authResponse.localId,
            displayName = authResponse.displayName?.ifBlank { null } ?: command.email.substringBefore("@"),
            email = authResponse.email,
            profilePictureUrl = null,
        )

        sessionManager.persistFirebaseSession(
            user = user,
            idToken = authResponse.idToken,
            refreshToken = authResponse.refreshToken
        )

        return user
    }

    actual override suspend fun register(command: RegisterUserCommand) {
        val authResponse = httpClient.post(firebaseAuthUrl("signUp")) {
            setBody(
                FirebaseSignInRequest(
                    email = command.email,
                    password = command.password
                )
            )
        }.body<FirebaseAuthResponse>()

        val user = UserAccount(
            activeProfileId = authResponse.localId,
            displayName = command.username,
            email = command.email,
            profilePictureUrl = null,
            role = UserRole.USER,
            banned = false
        )

        upsertUserDocument(
            uid = authResponse.localId,
            idToken = authResponse.idToken,
            user = user
        )

        sessionManager.persistFirebaseSession(
            user = user,
            idToken = authResponse.idToken,
            refreshToken = authResponse.refreshToken
        )
    }

    actual override suspend fun update() {
        val refreshToken = sessionManager.refreshToken.value ?: return
        val refreshResponse = httpClient.post(firebaseRefreshUrl) {
            setBody(
                FirebaseRefreshRequest(
                    grantType = "refresh_token",
                    refreshToken = refreshToken
                )
            )
        }.body<FirebaseRefreshResponse>()

        val currentUser = sessionManager.currentUserAccount.value ?: fetchUserAccount(
            uid = refreshResponse.userId,
            idToken = refreshResponse.idToken
        ) ?: return

        sessionManager.persistFirebaseSession(
            user = currentUser,
            idToken = refreshResponse.idToken,
            refreshToken = refreshResponse.refreshToken
        )
    }

    private suspend fun fetchUserAccount(uid: String, idToken: String): UserAccount? {
        val response = runCatching {
            httpClient.get(firestoreDocumentUrl(uid)) {
                header(HttpHeaders.Authorization, "Bearer $idToken")
            }.body<FirestoreUserDocument>()
        }.getOrNull() ?: return null

        return response.toUserAccount()
    }

    private suspend fun upsertUserDocument(uid: String, idToken: String, user: UserAccount) {
        httpClient.patch(firestoreDocumentUrl(uid)) {
            header(HttpHeaders.Authorization, "Bearer $idToken")
            setBody(FirestoreUserDocument.fromUserAccount(uid, user))
        }
    }

    private fun firebaseAuthUrl(method: String): String =
        "https://identitytoolkit.googleapis.com/v1/accounts:$method?key=${DesktopFirebaseConfig.apiKey}"

    private fun firestoreDocumentUrl(uid: String): String =
        "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/users/$uid"

    private val firebaseRefreshUrl: String
        get() = "https://securetoken.googleapis.com/v1/token?key=${DesktopFirebaseConfig.apiKey}"
}

@Serializable
private data class FirebaseSignInRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

@Serializable
private data class FirebaseAuthResponse(
    @SerialName("localId")
    val localId: String,
    @SerialName("idToken")
    val idToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    val email: String? = null,
    val displayName: String? = null
)

@Serializable
private data class FirebaseRefreshRequest(
    @SerialName("grant_type")
    val grantType: String,
    @SerialName("refresh_token")
    val refreshToken: String
)

@Serializable
private data class FirebaseRefreshResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("id_token")
    val idToken: String,
    @SerialName("refresh_token")
    val refreshToken: String
)

@Serializable
private data class FirestoreUserDocument(
    val name: String? = null,
    val fields: Map<String, FirestoreValue> = emptyMap()
) {
    fun toUserAccount(): UserAccount {
        return UserAccount(
            activeProfileId = fields["activeProfileId"]?.stringValue,
            displayName = fields["displayName"]?.stringValue,
            email = fields["email"]?.stringValue,
            profilePictureUrl = fields["profilePictureUrl"]?.stringValue,
            role = fields["role"]?.stringValue?.let { value ->
                runCatching { enumValueOf<UserRole>(value) }.getOrDefault(UserRole.USER)
            } ?: UserRole.USER,
            banned = fields["banned"]?.booleanValue ?: false
        )
    }

    companion object {
        fun fromUserAccount(uid: String, user: UserAccount): FirestoreUserDocument {
            val fields = buildMap<String, FirestoreValue> {
                put("activeProfileId", FirestoreValue(stringValue = user.activeProfileId ?: uid))
                user.displayName?.let { put("displayName", FirestoreValue(stringValue = it)) }
                user.email?.let { put("email", FirestoreValue(stringValue = it)) }
                user.profilePictureUrl?.let { put("profilePictureUrl", FirestoreValue(stringValue = it)) }
                put("role", FirestoreValue(stringValue = user.role.name))
                put("banned", FirestoreValue(booleanValue = user.banned))
            }
            return FirestoreUserDocument(fields = fields)
        }
    }
}

@Serializable
private data class FirestoreValue(
    val stringValue: String? = null,
    val booleanValue: Boolean? = null
)
