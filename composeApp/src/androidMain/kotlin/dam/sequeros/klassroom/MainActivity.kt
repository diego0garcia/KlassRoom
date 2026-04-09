package dam.sequeros.klassroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.russhwolf.settings.Settings
import dam.sequeros.klassroom.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.russhwolf.settings.SharedPreferencesSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val AndroidPlatformModule = module {
            single<Settings> {
                val sharedPrefs = androidContext().getSharedPreferences(
                    "mi_app_prefs",
                    android.content.Context.MODE_PRIVATE
                )
                SharedPreferencesSettings(sharedPrefs)
            }
        }

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule, AndroidPlatformModule)
        }

        setContent {
            App()
        }
    }
}
