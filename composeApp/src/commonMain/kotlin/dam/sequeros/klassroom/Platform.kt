package dam.sequeros.klassroom

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform