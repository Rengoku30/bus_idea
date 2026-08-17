package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TNTColorScheme = darkColorScheme(
    primary = TNTYellow,
    onPrimary = Color.Black,
    primaryContainer = TNTYellowDark,
    onPrimaryContainer = Color.Black,
    secondary = TNTYellowBright,
    onSecondary = Color.Black,
    tertiary = TNTGold,
    background = TNTDarkBackground,
    onBackground = TNTTextPrimary,
    surface = TNTDarkSurface,
    onSurface = TNTTextPrimary,
    surfaceVariant = TNTDarkCard,
    onSurfaceVariant = TNTTextSecondary,
    outline = TNTDarkInputBorder,
    outlineVariant = TNTDarkCardBorder,
    error = TNTError,
    onError = Color.White
)

@Composable
fun TNTBusTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TNTColorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    TNTBusTheme(content = content)
}
