package dam.sequeros.klassroom

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import dam.sequeros.klassroom.di.appModule
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import java.util.prefs.Preferences

val DesktopPlatformModule = module {
    single<Settings> {
        val preferences = Preferences.userRoot().node("mi.app")
        PreferencesSettings(preferences)
    }
}

fun main() = application {

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