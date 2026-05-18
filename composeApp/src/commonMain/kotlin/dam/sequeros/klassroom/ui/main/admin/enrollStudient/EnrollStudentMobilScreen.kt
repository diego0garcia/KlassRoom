package dam.sequeros.klassroom.ui.main.admin.enrollStudient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dam.sequeros.klassroom.ui.main.admin.adduser.common.OptionHeader
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.common.EnrollStudentFooter
import dam.sequeros.klassroom.ui.main.admin.enrollStudient.common.EnrollStudentForm

@Composable
fun EnrollStudentMobilScreen(
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