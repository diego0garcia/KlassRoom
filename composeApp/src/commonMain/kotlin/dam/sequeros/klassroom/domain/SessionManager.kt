
package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.infraestructure.TokenStorage
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SessionManager (
    tokenStorage: TokenStorage,
    client: HttpClient
){
    val currentUserAccount: StateFlow<UserAccount?>
    fun setCurrentUser(user: UserAccount)

    suspend fun closeSession()
    suspend fun autoLogin(): Boolean

    //JVM
    fun logIn(user: UserAccount, idToken: String, refreshToken: String)
}