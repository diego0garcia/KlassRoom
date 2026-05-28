package dam.sequeros.klassroom.ui.main.home.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.domain.model.Course

@Composable
fun CardModule(
    onClick: () -> Unit,
    course: Course
){
    Card(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(min = 300.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = course.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Box(
                    modifier = Modifier.fillMaxWidth(0.9f).height(2.dp)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Text(
                    text = "Assigned Subjects: ${course.subjects.size}",
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}