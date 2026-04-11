package dam.sequeros.klassroom.ui.main.admin.adduser.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.common.RadioButton
import dam.sequeros.klassroom.ui.main.admin.AdminPanelViewModel

@Composable
fun RegisterForm(
    vm: ViewModel,
) {
    val vm = vm as AdminPanelViewModel
    val state by vm.state.collectAsState()
    val roleList =
        mapOf(
            "User" to UserRole.USER,
            "Student" to UserRole.STUDENT,
            "Teacher" to UserRole.TEACHER,
            "Admin" to UserRole.ADMIN
        )

    Column (
        Modifier.fillMaxWidth()
    ){
        CustomTextField(
            header = "Nombre de usuario",
            value = state.username,
            onValueChange = { vm.onUsernameChange(it.trimEnd()) },
            error = state.usernameError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Email",
            value = state.email,
            onValueChange = { vm.onEmailChange(it.trimEnd()) },
            error = state.emailError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Password",
            value = state.password,
            onValueChange = { vm.onPasswordChange(it.trimEnd()) },
            error = state.passwordError,
            isPassword = true,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(10.dp),
            text = "Rol",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Column(modifier = Modifier.padding(start = 20.dp)) {
            roleList.forEach { (code, role) ->
                RadioButton(
                    text = code,
                    isSelected = state.role == role,
                    onClick = {
                        vm.onRoleChange(role)
                    }
                )
            }
        }
    }
}