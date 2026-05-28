package dam.sequeros.klassroom.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.domain.SessionManager
import dam.sequeros.klassroom.domain.model.Course
import dam.sequeros.klassroom.ui.main.home.common.CardModule
import dam.sequeros.klassroom.ui.main.home.common.PersonalAreaHeader
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeMobilScreen(
    onClickModule: (Course) -> Unit,
) {
    val sessionManager: SessionManager = koinInject()
    val vm: HomeViewModel = koinViewModel()
    val user by sessionManager.currentUserAccount.collectAsState()
    val courseList by vm.courseList.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        PersonalAreaHeader(user)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.Gray)
                .padding(16.dp)
        ) {
                Text(
                    text = "Your Courses",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(courseList) { course ->
                        if (!course.subjects.isEmpty()) {
                            CardModule(
                                onClick = {
                                    onClickModule(course)
                                },
                                course = course,
                            )
                        }
                    }
                }
            }
        }
}