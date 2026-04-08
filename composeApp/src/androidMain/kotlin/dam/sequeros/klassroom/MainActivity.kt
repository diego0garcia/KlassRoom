package dam.sequeros.klassroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.russhwolf.settings.Settings
import dam.sequeros.klassroom.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val AndroidPlatformModule = module {
            single<Settings> {
                // Obtenemos el archivo de SharedPreferences usando el contexto de Android
                val sharedPrefs = androidContext().getSharedPreferences(
                    "mi_app_prefs",
                    android.content.Context.MODE_PRIVATE
                )
                // Aquí instanciarías tu implementación de Android
                AndroidSettings(sharedPrefs)
            }
        }

        startKoin {
            modules(appModule, AndroidPlatformModule)
        }

        setContent {
            App()
        }
    }
}
