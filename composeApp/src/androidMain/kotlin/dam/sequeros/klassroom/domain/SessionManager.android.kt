package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

actual class SessionManager actual constructor(
    private val tokenStorage: TokenStorage
){
    val db = Firebase.firestore
    val auth = Firebase.auth

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
        auth.signOut()
        _currentUserAccount.update { null }
    }

    actual fun recuperarSesion(): Boolean {
        TODO("Not yet implemented")
    }
}