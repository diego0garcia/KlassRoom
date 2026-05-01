package dam.sequeros.klassroom.ui.main.schedules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dam.sequeros.klassroom.ui.main.schedules.common.ScheduleHeader
import dam.sequeros.klassroom.ui.main.schedules.common.ScheduleTable

@Composable
fun SchedulesDesktopScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScheduleHeader()
        ScheduleTable()
    }
}