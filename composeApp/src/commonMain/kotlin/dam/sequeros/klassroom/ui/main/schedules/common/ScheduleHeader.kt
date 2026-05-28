package dam.sequeros.klassroom.ui.main.schedules.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScheduleHeader() {
    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Your schedule",
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(start = 16.dp)
            .height(2.dp)
            .background(MaterialTheme.colorScheme.primary)
    )

    Spacer(modifier = Modifier.height(8.dp))
}