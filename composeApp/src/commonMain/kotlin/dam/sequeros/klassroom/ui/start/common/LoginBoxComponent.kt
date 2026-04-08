package dam.sequeros.klassroom.ui.start.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.start.StartViewModel
import klassroom.composeapp.generated.resources.Res
import klassroom.composeapp.generated.resources.klass_room_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginBoxComponent(
    onLogin: () -> Unit,
) {
    val vm: StartViewModel = koinViewModel()
    val loginState by vm.state.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(Res.drawable.klass_room_logo),
            contentDescription = "Logo Imagen",
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .sizeIn(minWidth = 150.dp, maxWidth = 400.dp)
        )

        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxSize(0.7f),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CustomTextField(
                        header = "Username",
                        value = loginState.username,
                        onValueChange = {
                            vm.onUsernameChange(it.trimStart())
                        },
                        error = loginState.usernameError,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(
                        header = "Password",
                        value = loginState.password,
                        onValueChange = {
                            vm.onPasswordChange(it.trimStart())
                        },
                        error = loginState.passwordError,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    //MENSAJE DE ERROR GENERAL
                    if (loginState.errorMessage != null) {
                        Text(
                            text = loginState.errorMessage!!,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (loginState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(0.55f),
                            onClick = onLogin,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "LOGIN",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}