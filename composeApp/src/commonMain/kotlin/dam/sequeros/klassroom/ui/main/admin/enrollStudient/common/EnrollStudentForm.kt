package dam.sequeros.klassroom.ui.main.admin.enrollStudient.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.EnrollStudentViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrollStudentForm(
) {
    val vm: EnrollStudentViewModel = koinViewModel()
    val state by vm.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedCourseName by remember { mutableStateOf("Selecciona...") }

    LaunchedEffect(state.curseId, state.courses) {
        selectedCourseName = state.courses.firstOrNull { it.id == state.curseId }?.name ?: "Selecciona..."
    }

    Column(
        Modifier.fillMaxWidth()
    ) {
        CustomTextField(
            header = "Student DNI",
            value = state.dni,
            onValueChange = { vm.onDniChange(it.trimEnd()) },
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

        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = "Curso",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCourseName,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                shape = RoundedCornerShape(12.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.courses.isEmpty()) {
                    DropdownMenuItem(
                        modifier = Modifier.clip(shape = RoundedCornerShape(16.dp)),
                        text = {
                            Text(
                                text = "No courses found",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {}
                    )
                } else {
                    state.courses.forEach { course ->
                        DropdownMenuItem(
                            modifier = Modifier.clip(shape = RoundedCornerShape(16.dp)),
                            text = { Text(course.name) },
                            onClick = {
                                selectedCourseName = course.name
                                vm.onCurseIdChange(course.id)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        val curseIdError = state.curseIdError
        if (curseIdError != null) {
            Text(
                text = curseIdError,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
