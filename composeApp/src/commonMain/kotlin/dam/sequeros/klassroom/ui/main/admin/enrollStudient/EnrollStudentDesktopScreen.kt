package dam.sequeros.klassroom.ui.main.admin.enrollStudient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dam.sequeros.klassroom.ui.main.admin.adduser.AddUserViewModel
import dam.sequeros.klassroom.ui.main.admin.adduser.common.OptionHeader
import dam.sequeros.klassroom.ui.main.admin.adduser.common.RegisterForm
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.common.EnrollStudentFooter
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.common.EnrollStudentForm
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EnrollStudentDesktopScreen(
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OptionHeader(
            text = "Enroll Student",
            onBack = onBack
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {

            EnrollStudentForm()

            Spacer(modifier = Modifier.height(8.dp))

            EnrollStudentFooter()
        }
    }
}