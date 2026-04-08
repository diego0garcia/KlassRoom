package dam.sequeros.klassroom.ui.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dam.sequeros.klassroom.ui.start.common.LoginBoxComponent

@Composable
fun StartMobilScreen(
    onLogin: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoginBoxComponent(
            onLogin = onLogin,
        )
    }
}