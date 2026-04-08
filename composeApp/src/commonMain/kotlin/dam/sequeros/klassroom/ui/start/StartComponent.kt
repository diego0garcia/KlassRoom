package dam.sequeros.klassroom.ui.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dam.sequeros.klassroom.domain.AppSettings
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartComponent(
    onLogin: () -> Unit
) {
    val vm: StartViewModel = koinViewModel()
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()
    val loginState by vm.state.collectAsState()

    //OBSERVAMOS EL ESTADO DEL LOGIN Y CUANDO ES TRUE NAVEGAMOS A LA SIGUIENTE PANTALLA
    LaunchedEffect(loginState.isLoginSuccess){
        if (loginState.isLoginSuccess){
            onLogin()
        }
    }

    if (screenSize) {
        StartDesktopScreen(
            onLogin = {
                vm.onLogin()
            },
        )
    } else {
        StartMobilScreen(
            onLogin = {
                vm.onLogin()
            },
        )
    }
}