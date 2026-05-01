package dam.sequeros.klassroom.ui.main.admin.addsubject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import dam.sequeros.klassroom.ui.main.admin.adduser.common.OptionHeader
import dam.sequeros.klassroom.ui.main.admin.addsubject.common.SubjectForm
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddSubjectDesktopScreen(
    onBack: () -> Unit,
) {
    val vm: SubjectViewModel = koinViewModel()
    val state by vm.state.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OptionHeader(
            text = "Añadir Asignatura",
            onBack = onBack
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {
            SubjectForm(vm = vm)

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

                if (state.isAddedSuccess) {
                    Text(
                        text = "Asignatura guardada con éxito",
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
                        onClick = { vm.onAddSubject() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Guardar",
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
