package dam.sequeros.klassroom.ui.main.admin.addcurse.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.domain.model.Subject
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

@Composable
fun SubjectCard(
    subject: Subject,
    onEditSubject: () -> Unit,
    onDelete: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Prof ID: ${subject.teacherId ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Día ${subject.weekDay}  |  ${subject.startHour}:00 - ${subject.endHour}:00",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Row {
                FilledIconButton(
                    onClick = onEditSubject,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(20.dp))
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledIconButton(
                    onClick = onDelete,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

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

@Composable
fun AddSubjectForm(
    onClose: () -> Unit,
    onAdd: () -> Unit,
    isEdit: Boolean,
    isValid: Boolean,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isEdit) "Edit Subject" else "New Subject",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            SubjectForm()

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onClose,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancel", fontWeight = FontWeight.Bold)
                }

                Button(
                    enabled = isValid,
                    onClick = {
                        onAdd()
                        onClose()
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (isEdit) "Update" else "Add", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}