package dam.sequeros.klassroom.ui.main.home.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dam.sequeros.klassroom.domain.SessionManager
import org.koin.compose.koinInject

@Composable
fun UserInfo(

) {
    val sessionManager: SessionManager = koinInject()
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(sessionManager.currentUserAccount.value?.displayName ?: "John Doe")
        Text(sessionManager.currentUserAccount.value?.email ?: "johndoe@email.com")
        Text(sessionManager.currentUserAccount.value?.id ?: "Not Id")
        Text(sessionManager.currentUserAccount.value?.role?.name ?: "Not Role")
    }
}