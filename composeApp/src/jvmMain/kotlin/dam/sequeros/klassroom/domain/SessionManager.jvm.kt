package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.infraestructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

actual class SessionManager actual constructor(
    private val tokenStorage: TokenStorage
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val _currentUserAccount = MutableStateFlow<UserAccount?>(null)
    actual val currentUserAccount: StateFlow<UserAccount?> = _currentUserAccount.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    actual val accessToken = _accessToken.asStateFlow()

    private val _refreshToken = MutableStateFlow<String?>(null)
    actual val refreshToken = _refreshToken.asStateFlow()

    actual fun setCurrentUser(user: UserAccount) {
        _currentUserAccount.update { user }
    }

    actual suspend fun clearCurrentUser() {
        _currentUserAccount.update { null }
        _accessToken.update { null }
        _refreshToken.update { null }
        tokenStorage.clear()
    }

    actual fun recuperarSesion(): Boolean {
        val access = tokenStorage.getAccessToken()
        val refresh = tokenStorage.getRefreshToken()
        val data = tokenStorage.getDataToken()

        if (!data.isNullOrBlank()) {
            val user = runCatching {
                json.decodeFromString<UserAccount>(data)
            }.getOrElse {
                val tokenData = TokenJwt(data)
                UserAccount(
                    activeProfileId = tokenData.payload.userId!!,
                    displayName = tokenData.payload.userName!!,
                    email = tokenData.payload.userEmail ?: "",
                    profilePictureUrl = tokenData.payload.userImage,
                )
            }

            setCurrentUser(user)

            _accessToken.update { access }
            _refreshToken.update { refresh }
            return true
        }
        return false
    }

    fun persistFirebaseSession(
        user: UserAccount,
        idToken: String,
        refreshToken: String
    ) {
        tokenStorage.saveTokens(
            accessToken = idToken,
            refreshToken = refreshToken,
            dataToken = json.encodeToString(UserAccount.serializer(), user)
        )
        _accessToken.update { idToken }
        _refreshToken.update { refreshToken }
        setCurrentUser(user)
    }
}
