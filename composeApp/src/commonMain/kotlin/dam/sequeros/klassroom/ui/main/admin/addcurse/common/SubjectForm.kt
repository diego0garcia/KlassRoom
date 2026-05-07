package dam.sequeros.klassroom.ui.main.admin.addcurse.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.main.admin.addcurse.CurseViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectForm() {

    val vm: CurseViewModel = koinViewModel()
    val state by vm.subjectState.collectAsState()

    val teacherList by vm.teacherList.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Selecciona...") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTextField(
            header = "Nombre de la asignatura",
            value = state.name,
            onValueChange = { vm.onSubjectNameChange(it.trimEnd()) },
            error = state.nameError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = "Teacher",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
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

            ExposedDropdownMenu(
                shape = RoundedCornerShape(16.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (teacherList.isEmpty()) {
                    DropdownMenuItem(
                        modifier = Modifier.clip(shape = RoundedCornerShape(16.dp)),
                        text = {
                            Text(
                                text = "Not teachers founds"
                            )
                        },
                        onClick = {}
                    )
                } else {
                    teacherList.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier.clip(shape = RoundedCornerShape(16.dp)),
                            text = { Text(item.displayName!!) },
                            onClick = {
                                selectedText = item.displayName!!
                                expanded = false
                                vm.onSubjectTeacherIdChange(item.id!!)
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(
                    header = "Día (1-5)",
                    value = state.weekDay,
                    onValueChange = { vm.onSubjectWeekDayChange(it.trimEnd()) },
                    error = state.weekDayError,
                    wordColor = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(
                    header = "Inicio",
                    value = state.startHour,
                    onValueChange = { vm.onSubjectStartHourChange(it.trimEnd()) },
                    error = state.startHourError,
                    wordColor = MaterialTheme.colorScheme.onSurface
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                CustomTextField(
                    header = "Fin",
                    value = state.endHour,
                    onValueChange = { vm.onSubjectEndHourChange(it.trimEnd()) },
                    error = state.endHourError,
                    wordColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}