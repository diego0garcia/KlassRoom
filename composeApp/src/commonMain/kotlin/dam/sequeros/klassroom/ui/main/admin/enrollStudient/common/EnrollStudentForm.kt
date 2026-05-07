package dam.sequeros.klassroom.ui.main.admin.enrollStudient.common

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
import dam.sequeros.klassroom.domain.model.users.UserRole
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.common.RadioButton
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.EnrollStudentViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EnrollStudentForm(
) {
    val vm: EnrollStudentViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    Column (
        Modifier.fillMaxWidth()
    ){
        CustomTextField(
            header = "Student DNI",
            value = state.dni,
            onValueChange = { vm.onNameChange(it.trimEnd()) },
            error = state.dniError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Student Name",
            value = state.name,
            onValueChange = { vm.onNameChange(it.trimEnd()) },
            error = state.nameError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Student Email",
            value = state.email,
            onValueChange = { vm.onEmailChange(it.trimEnd()) },
            error = state.emailError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Student Phone",
            value = state.phone,
            onValueChange = { vm.onPhoneChange(it.trimEnd()) },
            error = state.phoneError,
            isPassword = true,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Phone",
            value = state.phone,
            onValueChange = { vm.onPhoneChange(it.trimEnd()) },
            error = state.phoneError,
            isPassword = true,
            wordColor = Color.White
        )
    }
}