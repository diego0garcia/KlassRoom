package dam.sequeros.klassroom.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dam.sequeros.klassroom.domain.AppSettings
import org.koin.compose.koinInject

@Composable
fun MainComponent(
    onCloseSession: () -> Unit
){
    val vm: MainViewModel = koinInject()
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()

    if (screenSize){
        MainDesktopScreen(
            vm = vm,
            onCloseSession = onCloseSession
        )
    }else{
        MainMobilScreen(
            vm = vm,
            onCloseSession = onCloseSession
        )
    }
}