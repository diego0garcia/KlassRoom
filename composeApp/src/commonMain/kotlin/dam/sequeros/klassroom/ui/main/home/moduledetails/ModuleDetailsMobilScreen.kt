package dam.sequeros.klassroom.ui.main.home.moduledetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dam.sequeros.klassroom.domain.model.Course

@Composable
fun ModuleDetailsMobilScreen(
    onBack: () -> Unit,
    course: Course,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = onBack,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Text(
            text = course.name,
        )
    }
}