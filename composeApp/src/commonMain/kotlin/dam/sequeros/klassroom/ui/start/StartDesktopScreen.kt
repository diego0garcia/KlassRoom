package dam.sequeros.klassroom.ui.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dam.sequeros.klassroom.ui.start.common.CarouselComponent
import dam.sequeros.klassroom.ui.start.common.LoginBoxComponent

@Composable
fun StartDesktopScreen(
    onLogin: () -> Unit,
) {
    Row(
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            CarouselComponent()
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(0.9f)
                .background(Color.LightGray)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            LoginBoxComponent(
                onLogin = onLogin,
            )
        }
    }
}