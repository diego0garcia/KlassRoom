package dam.sequeros.klassroom.infraestructure

import com.russhwolf.settings.Settings

class TokenStorage(private val settings: Settings) {
    companion object {
        private const val KEY_ID_TOKEN = "id_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }

    fun saveTokens(refreshToken: String, idToken: String) {
        settings.putString(KEY_ID_TOKEN, idToken)
        settings.putString(KEY_REFRESH_TOKEN, refreshToken)
    }

    fun getRefreshToken(): String? =
        settings.getStringOrNull(KEY_REFRESH_TOKEN)

    fun getIdToken(): String? =
        settings.getStringOrNull(KEY_ID_TOKEN)

    fun clear() {
        settings.remove(KEY_REFRESH_TOKEN)
        settings.remove(KEY_ID_TOKEN)
    }
}
