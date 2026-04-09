package dam.sequeros.klassroom.infraestructure

import kotlinx.serialization.json.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock

data class TokenJwtHeader( val alg:String, val typ:String)
{

}
data class TokenJwtPayload(val claims: Map<String, Any> = emptyMap()){
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? = claims[key] as? T

    // Propiedades calculadas para los campos más comunes (opcional)
    val id: String? get() = get("user_id") ?: get("sub")
    val displayName: String? get() = get("name")
    val email: String? get() = get("email")
    val profilePictureUrl: String? get() = get("picture")
    val role: String?  get() = get("role")
}
data class TokenJwtFirma(val firma:String)


class TokenJwt(val rawToken: String) {

    val header: TokenJwtHeader
    val payload: TokenJwtPayload
    val firma: TokenJwtFirma

    private fun normalizeBase64(input: String): String {
        var result = input
            .replace('-', '+')
            .replace('_', '/')
        val paddingNeeded = (4 - result.length % 4) % 4
        result += "=".repeat(paddingNeeded)
        return result
    }

    init {
        val parts = rawToken.split(".")
        if (parts.size != 3) {
            throw IllegalArgumentException("Formato de token inválido. Debe tener 3 partes.")
        }
        header = decodeHeader(parts[0])
        payload = decodePayload(parts[1])
        firma = TokenJwtFirma(parts[2])
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodeHeader(base64Header: String): TokenJwtHeader {
        val normalized = normalizeBase64(base64Header)
        val jsonString = Base64.decode(normalized).decodeToString()
        val json = Json.parseToJsonElement(jsonString).jsonObject

        return TokenJwtHeader(
            alg = json["alg"]?.jsonPrimitive?.content ?: "HS256",
            typ = json["typ"]?.jsonPrimitive?.content ?: "JWT"
        )
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun decodePayload(base64Payload: String): TokenJwtPayload {
        val normalized = normalizeBase64(base64Payload)
        val jsonString = Base64.decode(normalized).decodeToString()
        val jsonElement = Json.parseToJsonElement(jsonString).jsonObject

        val claimsMap = jsonElement.mapValues { (_, value) -> value.toPrimitive() }
        return TokenJwtPayload(claimsMap)
    }

    private fun JsonElement.toPrimitive(): Any {
        return when (this) {
            is JsonPrimitive -> {
                if (isString) content
                else if (booleanOrNull != null) booleanOrNull!!
                else if (longOrNull != null) longOrNull!!
                else doubleOrNull!!
            }
            is JsonArray -> map { it.toPrimitive() }
            is JsonObject -> mapValues { it.value.toPrimitive() }
            else -> toString()
        }
    }
    fun isSessionValid(): Boolean {
       return  this.payload.get<Long>("exp")?.let {
            it> Clock.System.now().epochSeconds
        }?:false

    }
}