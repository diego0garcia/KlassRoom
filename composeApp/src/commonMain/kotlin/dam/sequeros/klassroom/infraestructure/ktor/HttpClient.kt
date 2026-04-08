package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor

import dam.sequeros.klassroom.domain.SessionManager
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createHttpClient(
    tokenStorage: TokenStorage,
    sessionManager: SessionManager,
    refreshUrl:String
): HttpClient {
    return HttpClient { // Puedes usar HttpClient(CIO), HttpClient(Darwin), etc.
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        //logs
        install(Logging) {

            //logger = Logger.DEFAULT
            logger = object : Logger {
                override fun log(message: String) {
                    println("KTOR CLIENT LOG: $message")
                }
            }
            level = LogLevel.ALL // O LogLevel.ALL para ver todo
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val access = tokenStorage.getAccessToken()
                    val refresh = tokenStorage.getRefreshToken()

                    if (!access.isNullOrBlank()) {
                        BearerTokens(accessToken = access, refreshToken = refresh ?: "")
                    } else null
                }

                refreshTokens {
                    val oldRefreshToken = tokenStorage.getRefreshToken() ?: return@refreshTokens null

                    val response = client.post(refreshUrl) {
                        markAsRefreshTokenRequest()
                        setBody(mapOf("refresh_token" to oldRefreshToken))
                    }

                    if (response.status == HttpStatusCode.OK) {
                        val data = response.body<Map<String, String>>()

                        val newAccess = data["access_token"] ?: ""
                        val newRefresh = data["refresh_token"] ?: oldRefreshToken
                        val idToken = data["id_token"] ?: ""

                        tokenStorage.saveTokens(newAccess, newRefresh, idToken)
                        sessionManager.recuperarSesion()

                        BearerTokens(newAccess, newRefresh)
                    } else {
                        null
                    }
                }
            }
        }

                install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }
}