package dam.sequeros.klassroom.ui.main.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.ui.main.MainViewModel
import dam.sequeros.klassroom.ui.main.admin.addcurse.AddCurseDesktopScreen
import dam.sequeros.klassroom.ui.main.admin.addcurse.AddCurseMobilScreen
import dam.sequeros.klassroom.ui.main.admin.addsubject.AddSubjectDesktopScreen
import dam.sequeros.klassroom.ui.main.admin.addsubject.AddSubjectMobilScreen
import dam.sequeros.klassroom.ui.main.admin.adduser.AddUserDesktopScreen
import dam.sequeros.klassroom.ui.main.admin.adduser.AddUserMobilScreen
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.EnrollStudentDesktopScreen
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.EnrollStudentMobilScreen
import org.koin.compose.koinInject

@Composable
fun AdminPanelComponent() {
    val appSettings: AppSettings = koinInject()
    val screenSize by appSettings.screenSize.collectAsState()
    val navController = rememberNavController()

    fun onAddUser(){ navController.navigate(AdminRoutes.AddUser) }
    fun onAddCurse(){ navController.navigate(AdminRoutes.AddCurse) }
    fun onAddSubject(){ navController.navigate(AdminRoutes.AddSubject) }
    fun onEnrollStudent(){ navController.navigate(AdminRoutes.EnrollStudent) }
    fun onBack(){ navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = AdminRoutes.Init
    ) {
        composable(AdminRoutes.Init) {
            if (screenSize) {
                AdminPanelDesktopScreen(
                    onAddUser = { onAddUser() },
                    onAddCurse = { onAddCurse() },
                    onAddSubject = { onAddSubject() },
                    onEnrollStudent = { onEnrollStudent() }
                )
            } else {
                AdminPanelMobilScreen(
                    onAddUser = { onAddUser() },
                    onAddCurse = { onAddCurse() },
                    onAddSubject = { onAddSubject() },
                    onEnrollStudent = { onEnrollStudent() }
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

        composable(AdminRoutes.AddCurse) {
            if (screenSize) {
                AddCurseDesktopScreen(
                    onBack = { onBack() }
                )
            } else {
                AddCurseMobilScreen (
                    onBack = { onBack() }
                )
            }
        }

        composable(AdminRoutes.AddSubject) {
            if (screenSize) {
                AddSubjectDesktopScreen(
                    onBack = { onBack() }
                )
            } else {
                AddSubjectMobilScreen(
                    onBack = { onBack() }
                )
            }
        }

        composable(AdminRoutes.EnrollStudent) {
            if (screenSize) {
                EnrollStudentDesktopScreen(
                    onBack = { onBack() }
                )
            } else {
                EnrollStudentMobilScreen (
                    onBack = { onBack() }
                )
            }
        }
    }
}