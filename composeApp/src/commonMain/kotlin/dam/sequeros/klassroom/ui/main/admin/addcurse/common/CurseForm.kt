package dam.sequeros.klassroom.ui.main.admin.addcurse.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dam.sequeros.klassroom.ui.common.CustomTextField
import dam.sequeros.klassroom.ui.main.admin.addcurse.CurseViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurseForm() {
    val vm: CurseViewModel = koinViewModel()
    val state by vm.state.collectAsState()
    val subjectState by vm.subjectState.collectAsState()

    var isFromOpen by remember { mutableStateOf(false) }
    var isEdit by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Course Details",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CustomTextField(
            header = "Curse Name",
            value = state.name,
            onValueChange = { vm.onNameChange(it) },
            error = state.nameError,
            wordColor = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Subjects",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "${state.subjects.size} subjects added",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (!isFromOpen) {
                Button(
                    onClick = {
                        isEdit = false
                        vm.onClearSubjectForm()
                        isFromOpen = true
                    },
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(Modifier.width(8.dp))

                    Text("Add")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isFromOpen) {
            AddSubjectForm(
                isEdit = isEdit,
                onClose = { isFromOpen = false },
                onAdd = {
                    if (isEdit) {
                        // Llamar a vm.onEditSubject con el índice que toca
                    } else {
                        vm.onAddSubject()
                    }
                },
                isValid = subjectState.isValid
            )
        } else {
            if (state.subjects.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No subjects yet", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.subjects.forEachIndexed { index, subject ->
                        SubjectCard(
                            subject = subject,
                            onEditSubject = {
                                vm.onEditForm(subject)
                                isEdit = true
                                isFromOpen = true
                            },
                            onDelete = { vm.onDeleteSubject(subject) }
                        )
                    }
                }
            }
        }
    }
}