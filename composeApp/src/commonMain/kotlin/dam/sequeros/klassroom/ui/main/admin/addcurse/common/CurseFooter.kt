package dam.sequeros.klassroom.ui.main.admin.addcurse.common

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
import dam.sequeros.klassroom.ui.main.admin.addcurse.CurseViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurseFooter(){

    val vm : CurseViewModel = koinViewModel()
    val state by vm.state.collectAsState()

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
                onClick = {
                    vm.onAddCurse()
                },
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