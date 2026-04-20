
package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import dam.sequeros.klassroom.infraestructure.TokenStorage
import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SessionManager (
    tokenStorage: TokenStorage
){
    val currentUserAccount: StateFlow<UserAccount?>
    fun setCurrentUser(user: UserAccount)

    //JVM
    fun recoverSession(): Boolean
    fun logIn(user: UserAccount, idToken: String, refreshToken: String)
}