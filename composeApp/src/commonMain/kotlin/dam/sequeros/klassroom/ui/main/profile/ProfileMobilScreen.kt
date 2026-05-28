package dam.sequeros.klassroom.ui.main.profile

import androidx.compose.runtime.Composable
import dam.sequeros.klassroom.ui.main.profile.common.ProfileCommon

@Composable
fun ProfileMobilScreen(
    onCloseSession: () -> Unit,
) {
    ProfileCommon (
        onCloseSession = onCloseSession,
    )
}