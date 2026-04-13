package dam.sequeros.klassroom.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.ui.main.admin.AdminPanelComponent
import dam.sequeros.klassroom.ui.main.home.HomeComponent
import dam.sequeros.klassroom.ui.main.profile.ProfileComponent
import dam.sequeros.klassroom.ui.main.schedules.SchedulesComponent
import klassroom.composeapp.generated.resources.Res
import klassroom.composeapp.generated.resources.klass_room_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MainDesktopScreen(
    onCloseSession: () -> Unit
) {

    val sessionManager: SessionManager = koinInject()

    val user by sessionManager.currentUserAccount.collectAsState()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Row(
        Modifier.fillMaxSize()
    ) {
        NavigationRail(
            containerColor = Color.Black,
            contentColor = Color.White,
            header = {
                Box(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.klass_room_logo),
                        contentDescription = "Logo Imagen",
                        modifier = Modifier.size(86.dp)
                    )
                }
            }
        ) {
            //HOME
            NavigationRailItem(
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                selected = currentRoute == MainRoutes.Home,
                onClick = {
                    navController.navigate(route = MainRoutes.Home) {
                        launchSingleTop = true
                    }
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )

            //PROFILE
            NavigationRailItem(
                icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
                selected = currentRoute == MainRoutes.Profile,
                onClick = {
                    navController.navigate(route = MainRoutes.Profile) {
                        launchSingleTop = true
                    }
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )

            //ADMIN PANEL
            if (user?.role == UserRole.ADMIN) {
                NavigationRailItem(
                    icon = { Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = "Admin Panel") },
                    selected = currentRoute == MainRoutes.AdminPanel,
                    onClick = {
                        navController.navigate(route = MainRoutes.AdminPanel) {
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }

            //SCHEDULES
            if (user?.role == UserRole.TEACHER || user?.role == UserRole.ADMIN) {
                NavigationRailItem(
                    icon = { Icon(imageVector = Icons.Default.Schedule, contentDescription = "Schedules") },
                    selected = currentRoute == MainRoutes.Schedules,
                    onClick = {
                        navController.navigate(route = MainRoutes.Schedules) {
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            NavHost(
                navController = navController,
                startDestination = MainRoutes.Home
            ) {
                composable(route = MainRoutes.Home) {
                    HomeComponent()
                }
                composable(route = MainRoutes.Profile) {
                    ProfileComponent(
                        onCloseSession = onCloseSession
                    )
                }
                composable(route = MainRoutes.AdminPanel) {
                    AdminPanelComponent()
                }
                composable(route = MainRoutes.Schedules) {
                    SchedulesComponent()
                }
            }
        }
    }
}