package dam.sequeros.klassroom.ui.main.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.ui.main.MainViewModel
import dam.sequeros.klassroom.ui.main.admin.adduser.AddUserDesktopScreen
import dam.sequeros.klassroom.ui.main.admin.adduser.AddUserMobilScreen
import org.koin.compose.koinInject

@Composable
fun AdminPanelComponent(

) {
    val vm: MainViewModel = koinInject()
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()
    val navController = rememberNavController()

    fun onAddUser(){ navController.navigate(AdminRoutes.AddUser) }
    fun onBack(){ navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = AdminRoutes.Init
    ) {
        composable(AdminRoutes.Init) {
            if (screenSize) {
                AdminPanelDesktopScreen(
                    onAddUser = { onAddUser() }
                )
            } else {
                AdminPanelMobilScreen(
                    onAddUser = { onAddUser() }
                )
            }
        }

        composable(AdminRoutes.AddUser) {
            if (screenSize) {
                AddUserDesktopScreen(
                    onBack = { onBack() }
                )
            } else {
                AddUserMobilScreen(
                    onBack = { onBack() }
                )
            }
        }
    }
}