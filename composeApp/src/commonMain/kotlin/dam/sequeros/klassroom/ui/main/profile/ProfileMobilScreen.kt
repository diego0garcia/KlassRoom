package dam.sequeros.klassroom.ui.main.profile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProfileMobilScreen(
    onCloseSession: () -> Unit,
) {
    Button(
        onClick = onCloseSession
    ){
        Text("Close Session")
    }
}