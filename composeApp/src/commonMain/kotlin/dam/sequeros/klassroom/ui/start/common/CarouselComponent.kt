package dam.sequeros.klassroom.ui.start.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CarouselComponent() {
    Card(
        modifier = Modifier
            .fillMaxSize(0.8f),
        colors = CardDefaults.cardColors(
            containerColor = Color.Red
        )
    ) {
        Text("CARRUSEL DE IMAGENES DE FUNCIONALIDADES")
    }
}