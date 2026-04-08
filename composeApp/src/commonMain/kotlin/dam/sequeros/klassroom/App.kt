package dam.sequeros.klassroom

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.ui.AppRoutes
import dam.sequeros.klassroom.ui.AppTheme
import dam.sequeros.klassroom.ui.main.MainComponent
import dam.sequeros.klassroom.ui.start.StartComponent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {

    val appSettings: AppSettings = koinInject()
    val navController: NavHostController = rememberNavController()

    val isDarkTheme = appSettings.isDarkMode.collectAsState()
    val screenSize by appSettings.screenSize.collectAsState()

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {

        //PARA CALCULAR DE FORMA REACTIVA EL TAMAÑO DE LA PANTALLA
        when {
            maxWidth < 600.dp && screenSize -> appSettings.setScreenSize(false)
            maxWidth > 600.dp && !screenSize -> appSettings.setScreenSize(true)
        }

        AppTheme(isDarkTheme) {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.Start
                ){
                    //PANTALLA DE INICIO
                    composable(route = AppRoutes.Start){
                        StartComponent(
                            onLogin = {
                                navController.navigate(AppRoutes.Main){
                                    popUpTo(AppRoutes.Start) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    //PANTALLA DE HOME (LA APP EN SÍ XD)
                    composable (route = AppRoutes.Main){
                        MainComponent(
                            onCloseSession = {
                                navController.navigate(AppRoutes.Start) {
                                    popUpTo(AppRoutes.Main) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}