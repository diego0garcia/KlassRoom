package dam.sequeros.klassroom.ui.main.schedules.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")
private val subjects = listOf(
    "Matemáticas", "Lengua", "Física", "Inglés", "Historia", "Biología", "Arte", "Educación Física"
)
//Franja para crear intervalos
private fun buildTimeSlots(startHour: Int, count: Int, intervalMinutes: Int): List<String> {
    return List(count) { index ->
        val totalMinutes = startHour * 60 + index * intervalMinutes
        val hour = totalMinutes / 60
        val minute = totalMinutes % 60
        "%02d:%02d".format(hour, minute)
    }
}

@Composable
fun ScheduleTable(
    modifier: Modifier = Modifier,
) {
    val timeSlots = buildTimeSlots(startHour = 8, count = 8, intervalMinutes = 55)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Hora",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier.width(80.dp)
            )
            days.forEach { day ->
                Text(
                    text = day,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        timeSlots.forEachIndexed { slotIndex, time ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = time,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = Modifier.width(80.dp)
                )
                days.forEachIndexed { dayIndex, _ ->
                    Text(
                        text = subjects[(slotIndex + dayIndex) % subjects.size],
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
