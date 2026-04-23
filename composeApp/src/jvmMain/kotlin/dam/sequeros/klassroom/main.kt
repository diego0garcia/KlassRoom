package dam.sequeros.klassroom

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import dam.sequeros.klassroom.di.appModule
import dam.sequeros.klassroom.infraestructure.firebase.DesktopFirebaseConfig
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import dev.gitlive.firebase.FirebaseOptions
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import java.util.prefs.Preferences

val DesktopPlatformModule = module {
    single<Settings> {
        val preferences = Preferences.userRoot().node("mi.app")
        PreferencesSettings(preferences)
    }
}

/*
fun main() = application {

    Firebase.initialize(
        options = FirebaseOptions(
            applicationId = DesktopFirebaseConfig.appId,
            apiKey = DesktopFirebaseConfig.apiKey,
            projectId = DesktopFirebaseConfig.projectId
        )
    )

    startKoin {
        modules(appModule, DesktopPlatformModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "KlassRoom",
    ) {
        App()
    }
}

 */

fun main() {
    // 1. Koin lo primero, fuera de application para que sea global
    startKoin {
        modules(appModule, DesktopPlatformModule)
    }

    // 2. Intentamos inicializar Firebase, pero si falla no dejamos que mate la app
    try {
        Firebase.initialize(
            options = FirebaseOptions(
                applicationId = DesktopFirebaseConfig.appId,
                apiKey = DesktopFirebaseConfig.apiKey,
                projectId = DesktopFirebaseConfig.projectId
            )
        )
    } catch (e: Exception) {
        println("WARN: SDK de Firebase no disponible en esta plataforma: ${e.message}")
        // No lanzamos error, permitimos que la app siga
    }

    application {
        Window(onCloseRequest = ::exitApplication, title = "KlassRoom") {
            App()
        }
    }
}