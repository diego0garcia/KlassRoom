package dam.sequeros.klassroom.infraestructure.ktor

import dam.sequeros.klassroom.infraestructure.TokenStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createHttpClient(
    tokenStorage: TokenStorage,
    refreshUrl:String
): HttpClient {
    return HttpClient {
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
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
                encodeDefaults = true
            })
        }
        install(Auth) {
            bearer {
                /*
                loadTokens {
                    val access = tokenStorage.getAccessToken()
                    val refresh = tokenStorage.getRefreshToken()

                    if (!access.isNullOrBlank()) {
                        BearerTokens(accessToken = access, refreshToken = refresh ?: "")
                    } else null
                }
                 */

                sendWithoutRequest { request ->
                    val url = request.url.toString()
                    !url.contains("identitytoolkit.googleapis.com") && !url.contains("securetoken.googleapis.com")
                }

                refreshTokens {
                    val oldRefreshToken = tokenStorage.getRefreshToken() ?: return@refreshTokens null

                    val response = client.post(refreshUrl) {
                        markAsRefreshTokenRequest()
                        contentType(ContentType.Application.FormUrlEncoded)
                        setBody(
                            FormDataContent(
                                Parameters.build {
                                    append("grant_type", "refresh_token")
                                    append("refresh_token", oldRefreshToken)
                                }
                            )
                        )
                    }

                    if (response.status == HttpStatusCode.OK) {
                        val data = response.body<Map<String, String>>()

                        val newRefresh = data["refresh_token"] ?: oldRefreshToken
                        val idToken = data["id_token"] ?: ""

                        tokenStorage.saveTokens( newRefresh, idToken)
                        tokenStorage.saveTokens(newRefresh, idToken)

                        BearerTokens(idToken, newRefresh)
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
