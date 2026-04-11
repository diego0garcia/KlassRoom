package dam.sequeros.klassroom.ui.main.admin.adduser

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.common.RadioButton
import dam.sequeros.klassroom.ui.main.admin.AdminPanelViewModel
import dam.sequeros.klassroom.ui.main.admin.adduser.common.OptionHeader
import dam.sequeros.klassroom.ui.main.admin.adduser.common.RegisterForm
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddUserDesktopScreen(
    onBack: () -> Unit,
) {

    val vm: AdminPanelViewModel = koinViewModel()
    val state by vm.state.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OptionHeader(
            text = "Añadir Usuario",
            onBack = onBack
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {

            RegisterForm(vm = vm)

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.isRegisterSuccess) {
                    Text(
                        text = "Registrado con éxito",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Green
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(50.dp),
                        onClick = { vm.onRegisterUser() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Register",
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}