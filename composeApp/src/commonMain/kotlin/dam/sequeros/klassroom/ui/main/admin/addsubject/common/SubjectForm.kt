package dam.sequeros.klassroom.ui.main.admin.addsubject.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.main.admin.addsubject.SubjectViewModel

@Composable
fun SubjectForm(
    vm: ViewModel,
) {
    val vm = vm as SubjectViewModel
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTextField(
            header = "Nombre de la asignatura",
            value = state.name,
            onValueChange = { vm.onNameChange(it.trimEnd()) },
            error = state.nameError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "ID del profesor",
            value = state.teacherId,
            onValueChange = { vm.onTeacherIdChange(it.trimEnd()) },
            error = state.teacherIdError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Día de la semana (1-5)",
            value = state.weekDay,
            onValueChange = { vm.onWeekDayChange(it.trimEnd()) },
            error = state.weekDayError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Hora de inicio",
            value = state.startHour,
            onValueChange = { vm.onStartHourChange(it.trimEnd()) },
            error = state.startHourError,
            wordColor = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            header = "Hora de fin",
            value = state.endHour,
            onValueChange = { vm.onEndHourChange(it.trimEnd()) },
            error = state.endHourError,
            wordColor = Color.White
        )
    }
}
