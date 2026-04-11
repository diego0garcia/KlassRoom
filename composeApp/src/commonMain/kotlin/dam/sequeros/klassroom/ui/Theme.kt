package dam.sequeros.klassroom.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import klassroom.composeapp.generated.resources.Res
import klassroom.composeapp.generated.resources.spline_sans_font
import org.jetbrains.compose.resources.Font

@Composable
fun appTypography(): Typography {
    val splineSansFamily = FontFamily(
        Font(Res.font.spline_sans_font, FontWeight.Normal),
        Font(Res.font.spline_sans_font, FontWeight.Bold)
    )

    val default = Typography()

    return Typography(
        displayLarge = default.displayLarge.copy(fontFamily = splineSansFamily),
        displayMedium = default.displayMedium.copy(fontFamily = splineSansFamily),
        displaySmall = default.displaySmall.copy(fontFamily = splineSansFamily),
        headlineLarge = default.headlineLarge.copy(fontFamily = splineSansFamily),
        headlineMedium = default.headlineMedium.copy(fontFamily = splineSansFamily),
        headlineSmall = default.headlineSmall.copy(fontFamily = splineSansFamily),
        titleLarge = default.titleLarge.copy(fontFamily = splineSansFamily),
        titleMedium = default.titleMedium.copy(fontFamily = splineSansFamily),
        titleSmall = default.titleSmall.copy(fontFamily = splineSansFamily),
        bodyLarge = default.bodyLarge.copy(fontFamily = splineSansFamily),
        bodyMedium = default.bodyMedium.copy(fontFamily = splineSansFamily),
        bodySmall = default.bodySmall.copy(fontFamily = splineSansFamily),
        labelLarge = default.labelLarge.copy(fontFamily = splineSansFamily),
        labelMedium = default.labelMedium.copy(fontFamily = splineSansFamily),
        labelSmall = default.labelSmall.copy(fontFamily = splineSansFamily)
    )
}

private val LightColors = lightColorScheme(
    primary = Color(0xFF2D53A4),
    onPrimary = Color.White,
    secondary = Color(0xFF006D3E),
    onSecondary = Color.White,
    background = Color(0xFFFDFDFD),
    onBackground = Color(0xFF1C1B1F),
    error = Color(0xFFE50B3E),
    onError = Color(0xFFE5B20B),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF2D53A4),
    onPrimary = Color(0xFF003258),
    secondary = Color(0xFF8CDDA9),
    onSecondary = Color(0xFF00391F),
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE5E1E6),
)

@Composable
fun AppTheme(
    darkTheme: State<Boolean>,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme.value) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content,
        typography = appTypography()
    )
}