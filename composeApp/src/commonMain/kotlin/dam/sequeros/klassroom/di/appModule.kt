package dam.sequeros.klassroom.di

import dam.sequeros.klassroom.AppViewModel
import dam.sequeros.klassroom.aplication.usecase.LoginUserUseCase
import dam.sequeros.klassroom.aplication.usecase.RegisterUserUseCase
import dam.sequeros.klassroom.domain.AppSettings
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.repository.IAuthRepository
import dam.sequeros.klassroom.infraestructure.firebase.FirebaseAuthRepository
import dam.sequeros.klassroom.ui.main.MainViewModel
import dam.sequeros.klassroom.ui.start.StartViewModel
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //APPLICATION LAYER
    single { AppSettings() }
    single { TokenStorage(get()) }
    single { SessionManager(get()) }

    //PRESENTATION LAYER
    viewModel { AppViewModel(get()) }
    viewModel { StartViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }

    factory { LoginUserUseCase(get()) }
    factory { RegisterUserUseCase(get()) }

    //DOMAIN LAYER
    single <IAuthRepository>{ FirebaseAuthRepository(get()) }
}