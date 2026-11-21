package com.devdenisviana.divideai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val EsquemaEscuro = darkColorScheme(
    primary = Ambar,
    onPrimary = TextoEscuro,
    secondary = Verde,
    onSecondary = TextoEscuro,
    background = Fundo,
    onBackground = TextoClaro,
    surface = Superficie,
    onSurface = TextoClaro
)

@Composable
fun DivideAiTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaEscuro,
        typography = Typography,
        content = content
    )
}