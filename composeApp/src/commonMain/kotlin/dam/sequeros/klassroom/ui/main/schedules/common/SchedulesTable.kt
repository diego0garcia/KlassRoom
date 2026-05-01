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
import androidx.compose.ui.draw.clip
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
        return subjects.firstOrNull {
            it.weekDay == day && it.startHour.split(":")[0].toInt() == hour
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {
            Text("Hora", modifier = Modifier.width(80.dp), fontWeight = FontWeight.Bold)

            days.forEach {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        (8..15).forEachIndexed { index, hour ->

            val backgroundColor =
                if (index % 2 == 0) MaterialTheme.colorScheme.surface
                else MaterialTheme.colorScheme.surfaceVariant

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(backgroundColor)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "$hour:00",
                    modifier = Modifier.width(80.dp),
                    fontWeight = FontWeight.SemiBold
                )

                for (day in 1..5) {
                    val subject = findSubject(day, hour, subjects)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(
                                if (subject != null)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                                else Color.Transparent
                            )
                            .padding(6.dp),
                        contentAlignment = Alignment.Center

                    ) {
                        Text(
                            text = subject?.name ?: "-",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}