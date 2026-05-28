package dam.sequeros.klassroom.di

import dam.sequeros.klassroom.AppViewModel
import dam.sequeros.klassroom.aplication.usecase.AddCurseUseCase
import dam.sequeros.klassroom.aplication.usecase.AddSubjectUseCase
import dam.sequeros.klassroom.aplication.usecase.GetAllTeachersUseCase
import dam.sequeros.klassroom.aplication.usecase.GetCoursesUseCase
import dam.sequeros.klassroom.aplication.usecase.GetCourseByTeacherUseCase
import dam.sequeros.klassroom.aplication.usecase.GetSubjectsUseCase
import dam.sequeros.klassroom.aplication.usecase.LoginUserUseCase
import dam.sequeros.klassroom.aplication.usecase.RegisterUserUseCase
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IAdminRepository
import dam.sequeros.klassroom.domain.repository.IAuthRepository
import dam.sequeros.klassroom.domain.repository.IScheduleRepository
import dam.sequeros.klassroom.domain.repository.IUtilsRepository
import dam.sequeros.klassroom.infraestructure.TokenStorage
import dam.sequeros.klassroom.infraestructure.firebase.FirebaseAdminRepository
import dam.sequeros.klassroom.infraestructure.firebase.FirebaseAuthRepository
import dam.sequeros.klassroom.infraestructure.firebase.FirebaseScheduleRepository
import dam.sequeros.klassroom.infraestructure.firebase.FirebaseUtilsRepository
import dam.sequeros.klassroom.infraestructure.ktor.createHttpClient
import dam.sequeros.klassroom.ui.main.MainViewModel
import dam.sequeros.klassroom.ui.main.admin.AdminPanelViewModel
import dam.sequeros.klassroom.ui.main.admin.addcurse.CurseViewModel
import dam.sequeros.klassroom.ui.main.profile.ProfileViewModel
import dam.sequeros.klassroom.ui.main.admin.addsubject.SubjectViewModel
import dam.sequeros.klassroom.ui.main.admin.adduser.AddUserViewModel
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.EnrollStudentViewModel
import dam.sequeros.klassroom.ui.main.home.HomeViewModel
import dam.sequeros.klassroom.ui.main.schedules.ScheduleViewModel
import dam.sequeros.klassroom.ui.start.StartViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //APPLICATION LAYER
    single { AppSettings() }
    single { TokenStorage(get()) }
    single { SessionManager(get(), get()) }

    //PRESENTATION LAYER
    viewModel { AppViewModel(get()) }
    viewModel { StartViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }
    viewModel { AdminPanelViewModel() }
    viewModel { AddUserViewModel(get()) }
    viewModel { ScheduleViewModel(get(), get()) }
    viewModel { SubjectViewModel(get()) }
    viewModel { ProfileViewModel() }
    viewModel { EnrollStudentViewModel(get()) }
    viewModel { CurseViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }

    factory { LoginUserUseCase(get()) }
    factory { RegisterUserUseCase(get()) }
    factory { GetSubjectsUseCase(get()) }
    factory { AddSubjectUseCase(get()) }
    factory { AddCurseUseCase(get()) }
    factory { GetAllTeachersUseCase(get()) }
    factory { GetCoursesUseCase(get()) }
    factory { GetCourseByTeacherUseCase(get()) }

    //DOMAIN LAYER
    single <IAuthRepository>{ FirebaseAuthRepository(get(), get()) }
    single <IScheduleRepository>{ FirebaseScheduleRepository(get(), get()) }
    single <IAdminRepository>{ FirebaseAdminRepository(get(), get()) }
    single <IUtilsRepository>{ FirebaseUtilsRepository(get(), get()) }

    //INFRASTRUCTURE LAYER
    single {
        createHttpClient(
            get(),
            "https://securetoken.googleapis.com/v1/token?key=AIzaSyCiiLh4Ak_46BVnKFqvpbL9IRx9AbRs1MU"
        )
    }
}