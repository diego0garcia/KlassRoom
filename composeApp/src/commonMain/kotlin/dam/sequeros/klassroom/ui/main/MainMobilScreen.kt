package dam.sequeros.klassroom.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dam.sequeros.klassroom.domain.SessionManager
import org.koin.compose.koinInject

@Composable
fun MainMobilScreen(
    vm: MainViewModel,
    onCloseSession: () -> Unit
) {
    val sessionManager: SessionManager = koinInject()

    Column(
        Modifier.fillMaxSize()
    ) {
        Text(sessionManager.currentUserAccount.value?.email ?: "johndoe@email.com")

        Button(
            onClick = onCloseSession
        ) {
            Text(
                "CLOSE SESSION"
            )
        }
    }
}