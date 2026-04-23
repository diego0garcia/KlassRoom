package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.infraestructure.TokenJwt
import dam.sequeros.klassroom.infraestructure.TokenStorage
import dam.sequeros.klassroom.infraestructure.firebase.DesktopFirebaseConfig
import dam.sequeros.klassroom.infraestructure.firebase.FirestoreDocument
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

actual class SessionManager actual constructor(
    private val tokenStorage: TokenStorage,
    private val client: HttpClient
) {
    private val _currentUserAccount = MutableStateFlow<UserAccount?>(null)
    actual val currentUserAccount: StateFlow<UserAccount?> = _currentUserAccount.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    private val _idToken = MutableStateFlow<String?>(null)
    val idToken = _idToken.asStateFlow()

    private val _dataToken = MutableStateFlow<String?>(null)
    val dataToken = _dataToken.asStateFlow()

    actual fun setCurrentUser(user: UserAccount) {
        _currentUserAccount.update { user }
    }

    actual fun logIn(user: UserAccount, idToken: String, refreshToken: String) {
        _currentUserAccount.update { user }
        _idToken.update { idToken }
        tokenStorage.saveTokens(refreshToken, idToken)
    }

    actual suspend fun closeSession(){
        _currentUserAccount.update { null }
        tokenStorage.clear()
    }

    actual suspend fun autoLogin(): Boolean {
        val refreshToken = tokenStorage.getRefreshToken()
        if (refreshToken.isNullOrBlank()) return false

        return try {
            val response = client.post("https://securetoken.googleapis.com/v1/token?key=${DesktopFirebaseConfig.apiKey}") {
                setBody(mapOf(
                    "grant_type" to "refresh_token",
                    "refresh_token" to refreshToken
                ))
            }

            if (response.status.isSuccess()) {
                val refreshData: RefreshTokenResponse = response.body()
                val user = fetchUserFromFirestore(refreshData.userId, refreshData.idToken)

                if (user != null) {
                    logIn(user, refreshData.idToken, refreshData.refreshToken)
                    return true
                }
            }
            false
        } catch (e: Exception) {
            println("AutoLogin Error: ${e.message}")
            false
        }
    }

    private suspend fun fetchUserFromFirestore(localId: String, idToken: String): UserAccount? {
        val response = client.get(
            urlString = "https://firestore.googleapis.com/v1/projects/${DesktopFirebaseConfig.projectId}/databases/(default)/documents/users/$localId"
        ) {
            headers.append("Authorization", "Bearer $idToken")
        }

        return if (response.status.isSuccess()) {
            val data: FirestoreDocument = response.body()
            UserAccount(
                id = localId,
                displayName = data.fields["displayName"]?.stringValue ?: "Sin nombre",
                email = data.fields["email"]?.stringValue ?: "",
                profilePictureUrl = data.fields["profilePictureUrl"]?.stringValue,
                role = UserRole.valueOf(data.fields["role"]?.stringValue ?: UserRole.USER.name)
            )
        } else null
    }
}

@Serializable
data class RefreshTokenResponse(
    @SerialName("expires_in") val expiresIn: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("id_token") val idToken: String,
    @SerialName("user_id") val userId: String,
    @SerialName("project_id") val projectId: String
)