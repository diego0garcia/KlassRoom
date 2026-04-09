package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.infraestructure.TokenJwt
import dam.sequeros.klassroom.infraestructure.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

actual class SessionManager actual constructor(
    private val tokenStorage: TokenStorage
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

    actual fun recoverSession(): Boolean {
        val data = tokenStorage.getIdToken()
        val refresh = tokenStorage.getRefreshToken()

        if (!data.isNullOrBlank()) {
            val tokenData = TokenJwt(data)
            val user = UserAccount(
                id = tokenData.payload.id ?: "",
                displayName =  tokenData.payload.displayName ?: "",
                email = tokenData.payload.email ?: "",
                profilePictureUrl = tokenData.payload.profilePictureUrl,
                role = UserRole.valueOf(tokenData.payload.role ?: UserRole.USER.name) ,
            )

            _currentUserAccount.update { user }
            _idToken.update { refresh }
            return true
        }
        return false
    }
}
