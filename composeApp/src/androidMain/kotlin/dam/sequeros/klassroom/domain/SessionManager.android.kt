package dam.sequeros.klassroom.domain

import androidx.lifecycle.viewModelScope
import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.infraestructure.TokenJwt
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import dam.sequeros.klassroom.infraestructure.TokenStorage
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

actual class SessionManager actual constructor(
    private val tokenStorage: TokenStorage,
    private val client: HttpClient
) {
    val db = Firebase.firestore
    val auth = Firebase.auth

    private val _currentUserAccount = MutableStateFlow<UserAccount?>(null)
    actual val currentUserAccount: StateFlow<UserAccount?> = _currentUserAccount.asStateFlow()


    actual fun setCurrentUser(user: UserAccount) {
        _currentUserAccount.update { user }
    }

    actual suspend fun autoLogin(): Boolean {
        val currentUser = auth.currentUser
        try {
            val document = db.collection("users").document(currentUser?.uid!!).get()
            val userAccount = document.data<UserAccount>()
            val completeUser = userAccount.copy(id = currentUser.uid)

            setCurrentUser(completeUser)
            return true

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


    actual suspend fun closeSession(){
        _currentUserAccount.update { null }
        auth.signOut()
    }

    actual fun logIn(user: UserAccount, idToken: String, refreshToken: String) {
        TODO()
    }
}
