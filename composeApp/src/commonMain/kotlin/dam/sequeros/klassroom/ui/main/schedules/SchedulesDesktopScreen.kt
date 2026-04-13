package dam.sequeros.klassroom.ui.main.schedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.ui.main.schedules.common.ScheduleTable

@Composable
fun SchedulesDesktopScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Horarios de profesores",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        ScheduleTable(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
