package dam.sequeros.klassroom.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun HomeDesktopScreen(
    onClickModule: (Course) -> Unit,
) {
    val sessionManager: SessionManager = koinInject()
    val vm: HomeViewModel = koinViewModel()
    val user by sessionManager.currentUserAccount.collectAsState()
    val courseList by vm.courseList.collectAsState()
    val isLoading: Boolean by vm.isLoading.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        PersonalAreaHeader(user)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.background),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
            ) {
                Text(
                    text = "Your Courses",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                if (isLoading) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
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
    }
}