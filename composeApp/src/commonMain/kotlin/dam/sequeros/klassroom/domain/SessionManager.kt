
package dam.sequeros.klassroom.domain

import dam.sequeros.klassroom.domain.model.users.UserAccount
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SessionManager (
    tokenStorage: TokenStorage
){
    val currentUserAccount: StateFlow<UserAccount?>
    val accessToken: StateFlow<String?>
    val refreshToken: StateFlow<String?>

    fun setCurrentUser(user: UserAccount)
    suspend fun clearCurrentUser()

    fun recuperarSesion(): Boolean
}