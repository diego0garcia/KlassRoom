package dam.sequeros.klassroom.infraestructure.firebase

internal object DesktopFirebaseConfig {
    private const val DEFAULT_API_KEY = "AIzaSyCiiLh4Ak_46BVnKFqvpbL9IRx9AbRs1MU"
    private const val DEFAULT_PROJECT_ID = "klassroom-ddc64"
    private const val DEFAULT_APP_ID = "1:892321441281:android:21e657888df9cbc65b8200"

    val apiKey: String =
        System.getProperty("firebase.apiKey")
            ?: System.getenv("KLASSROOM_FIREBASE_API_KEY")
            ?: DEFAULT_API_KEY

    val projectId: String =
        System.getProperty("firebase.projectId")
            ?: System.getenv("KLASSROOM_FIREBASE_PROJECT_ID")
            ?: DEFAULT_PROJECT_ID

    val appId: String =
        System.getProperty("firebase.appId")
            ?: System.getenv("KLASSROOM_FIREBASE_APP_ID")
            ?: DEFAULT_APP_ID
}
