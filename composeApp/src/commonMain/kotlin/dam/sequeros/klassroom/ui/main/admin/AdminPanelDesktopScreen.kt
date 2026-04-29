package dam.sequeros.klassroom.ui.main.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.ui.main.admin.common.AdminOptionButton
import dam.sequeros.klassroom.ui.main.home.common.UserInfo

@Composable
fun AdminPanelDesktopScreen(
    onAddUser: () -> Unit,
    onAddSubject: () -> Unit,
) {
    Column (
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Panel de administrador",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(2.dp)
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AdminOptionButton(
            icon = Icons.Default.Person2,
            text = "Añadir Usuario",
            onClick = onAddUser
        )

        Spacer(modifier = Modifier.height(10.dp))

        AdminOptionButton(
            icon = Icons.Default.School,
            text = "Añadir Asignatura",
            onClick = onAddSubject
        )
    }
}