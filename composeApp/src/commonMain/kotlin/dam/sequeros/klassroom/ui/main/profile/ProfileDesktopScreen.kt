package dam.sequeros.klassroom.ui.main.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.ui.common.CustomOptionButton
import dam.sequeros.klassroom.ui.common.Tag
import klassroom.composeapp.generated.resources.Res
import klassroom.composeapp.generated.resources.profile_example_light
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileDesktopScreen(
    onCloseSession: () -> Unit,
) {
    val vm: ProfileViewModel = koinViewModel()
    val sessionManager: SessionManager = koinInject()
    val userAccount by sessionManager.currentUserAccount.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.size(140.dp), contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f))
                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        val profileUrl = userAccount?.profilePictureUrl

                        if (!profileUrl.isNullOrEmpty()) {
                            AsyncImage(
                                model = profileUrl,
                                contentDescription = "Avatar de usuario",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            val avatarRes = Res.drawable.profile_example_light
                            Image(
                                painter = painterResource(avatarRes),
                                contentDescription = "Avatar por defecto",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { vm.pickProfileImage() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    userAccount?.displayName ?: "User",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    userAccount?.email ?: "",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Tag(text = (userAccount?.role?.name ?: "without role"))
                }

            }

            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .height(2.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }

            Spacer(Modifier.height(15.dp))

            Text(
                text = "App Settings",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )

            CustomOptionButton(text = "General Settings", icon = Icons.Default.Settings, onClick = { })
            CustomOptionButton(text = "More Information", icon = Icons.Default.Info, onClick = { })
            CustomOptionButton(text = "Account Settings", icon = Icons.Default.Person, onClick = { })
            CustomOptionButton(text = "Help", icon = Icons.AutoMirrored.Filled.HelpOutline, onClick = { })


            Spacer(Modifier.height(25.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                onClick = onCloseSession,
                border = BorderStroke(1.dp, Color.Red),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Red.copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    text = "Close Session",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
