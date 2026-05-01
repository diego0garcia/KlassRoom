package dam.sequeros.klassroom.ui.main.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.ui.common.CustomOptionButton

@Composable
fun AdminPanelMobilScreen(
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
                text = "Admin Panel",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(2.dp)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomOptionButton(
            icon = Icons.Default.PersonAdd,
            text = "Add User",
            onClick = onAddUser
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomOptionButton(
            icon = Icons.Default.School,
            text = "Añadir Asignatura",
            onClick = onAddSubject
        )
    }
}