package dam.sequeros.klassroom.ui.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.ui.main.MainViewModel
import org.koin.compose.koinInject

@Composable
fun HomeComponent(

) {
    val vm: MainViewModel = koinInject()
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()

    if (screenSize){
        HomeDesktopScreen()
    }else{
        HomeMobilScreen()
    }
}