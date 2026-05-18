package dam.sequeros.klassroom.ui.main.admin.addcurse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dam.sequeros.klassroom.ui.main.admin.addcurse.common.CurseFooter
import dam.sequeros.klassroom.ui.main.admin.addcurse.common.CurseForm
import dam.sequeros.klassroom.ui.main.admin.adduser.common.OptionHeader

@Composable
fun AddCurseMobilScreen(
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
            text = "Añadir Asignatura",
            onBack = onBack
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {
            CurseForm()

            Spacer(modifier = Modifier.height(8.dp))

            CurseFooter()
        }
    }
}
