package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.infraestructure.TokenJwt
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dam.sequeros.klassroom.infraestructure.TokenStorage
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


    actual fun setCurrentUser(user: UserAccount) {
        _currentUserAccount.update { user }
    }

    actual fun recoverSession(): Boolean {
        TODO()
    }

    actual fun logIn(user: UserAccount, idToken: String, refreshToken: String) {
        TODO()
    }
}
