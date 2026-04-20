package dam.sequeros.klassroom.ui.main.schedules.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.domain.model.Subject
import dam.sequeros.klassroom.ui.main.schedules.ScheduleViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScheduleTable() {
    val vm: ScheduleViewModel = koinViewModel()
    val subjects by vm.subjects.collectAsState()
    val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes")

    fun findSubject(day: Int, hour: Int, subjects: List<Subject>): Subject? {
        return subjects.firstOrNull { subject ->
            subject.weekDay == day && subject.startHour.split(":")[0].toInt() == hour
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(0.9f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(
                modifier = Modifier.width(100.dp),
                text = "Hora",
                fontWeight = FontWeight.Bold,
            )

            days.forEach {
                Text(
                    modifier = Modifier.weight(1f),
                    text = it,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(1.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        (8..15).forEach { hour ->
            Row(
                modifier = Modifier
                    .border(width = 1.dp, Color.LightGray),
                horizontalArrangement = Arrangement.Center,
            ) {
                Row (
                    modifier = Modifier.padding(10.dp)
                ){
                    Text("$hour:00", modifier = Modifier.width(100.dp))

                    for (day in 1..5) {
                        val subject = findSubject(day, hour, subjects)
                        Text(
                            text = subject?.name ?: "",
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
