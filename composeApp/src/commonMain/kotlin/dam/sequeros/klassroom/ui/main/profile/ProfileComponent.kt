package dam.sequeros.klassroom.ui.main.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dam.sequeros.klassroom.domain.AppSettings
import org.koin.compose.koinInject

@Composable
fun ProfileComponent(
    onCloseSession: () -> Unit
) {
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()

    if (screenSize){
        ProfileDesktopScreen(
            onCloseSession = onCloseSession
        )
    }else{
        ProfileMobilScreen(
            onCloseSession = onCloseSession
        )
    }
}