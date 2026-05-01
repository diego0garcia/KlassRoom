package dam.sequeros.klassroom.ui.main.home.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.domain.SessionManager
import org.koin.compose.koinInject

@Composable
fun PersonalAreaScreen() {

    val sessionManager: SessionManager = koinInject()
    val user by sessionManager.currentUserAccount.collectAsState()

    Column(
        Modifier.fillMaxSize()
    ) {
        //HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column() {
                Text(
                    text = "Personal Area",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "Welcome, ${user?.displayName ?: "John Doe"}!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Email Icon",
                    )
                    Text(
                        text = user?.email ?: "johndoe@email.com",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Role Icon",
                    )
                    Text(
                        text = user?.role?.name?.lowercase() ?: "without role",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .heightIn(min = 60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Tablón principal")
        }
    }
}