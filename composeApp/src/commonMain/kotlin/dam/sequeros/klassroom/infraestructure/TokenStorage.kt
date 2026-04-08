package ies.sequeros.dam.pmdm.gestionperifl.infraestructure

import com.russhwolf.settings.Settings

class TokenStorage(private val settings: Settings) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_DATA_TOKEN = "data_token"
    }

    fun saveTokens(accessToken: String, refreshToken: String, dataToken: String) {
        settings.putString(KEY_ACCESS_TOKEN, accessToken)
        settings.putString(KEY_REFRESH_TOKEN, refreshToken)
        settings.putString(KEY_DATA_TOKEN, dataToken)
    }
    fun getAccessToken(): String? =
        settings.getStringOrNull(KEY_ACCESS_TOKEN)

    fun getRefreshToken(): String? =
        settings.getStringOrNull(KEY_REFRESH_TOKEN)

    fun getDataToken(): String? =
        settings.getStringOrNull(KEY_DATA_TOKEN)

    fun clear() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
        settings.remove(KEY_DATA_TOKEN)
    }

}
