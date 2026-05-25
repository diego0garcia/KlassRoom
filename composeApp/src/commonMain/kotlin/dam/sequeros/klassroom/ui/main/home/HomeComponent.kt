package dam.sequeros.klassroom.ui.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.ui.main.home.moduledetails.ModuleDetailsDesktopScreen
import dam.sequeros.klassroom.ui.main.home.moduledetails.ModuleDetailsMobilScreen
import org.koin.compose.koinInject

@Composable
fun HomeComponent(

) {
    val vm: HomeViewModel = koinInject()
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()
    val navController = rememberNavController()
    val selectedCourse by vm.selectedCourse.collectAsState()

    fun onBack() {
        navController.popBackStack()
    }

    fun onNavigateModule() {
        navController.navigate(HomeRoutes.Module)
    }

    NavHost(
        navController = navController,
        startDestination = HomeRoutes.Home
    ) {
        composable(route = HomeRoutes.Home) {
            if (screenSize) {
                HomeDesktopScreen(
                    onClickModule = { course ->
                        vm.setSelectedCourse(course)
                        onNavigateModule()
                    }
                )
            } else {
                HomeMobilScreen(
                    onClickModule = { course ->
                        vm.setSelectedCourse(course)
                        onNavigateModule()
                    }
                )
            }
        }

        composable(route = HomeRoutes.Module) {
            selectedCourse?.let { course ->
                if (screenSize) {
                    ModuleDetailsDesktopScreen(
                        onBack = {
                            onBack()
                        },
                        course = course
                    )
                } else {
                    ModuleDetailsMobilScreen(
                        onBack = {
                            onBack()
                        },
                        course = course
                    )
                }
            } ?: LaunchedEffect(Unit) {
                onBack()
            }
        }
    }
}