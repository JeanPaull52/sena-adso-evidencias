package com.example.noteplusadmin.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary          = GreenPrimary,
    onPrimary        = GreenOnPrimary,
    primaryContainer = GreenLight,
    onPrimaryContainer = GreenDark,
    secondary        = GreenDark,
    onSecondary      = White,
    secondaryContainer = GreenLight,
    background       = White,
    onBackground     = TextPrimary,
    surface          = White,
    onSurface        = TextPrimary,
    error            = ErrorRed,
    onError          = White
)

private val DarkColors = darkColorScheme(
    primary          = GreenPrimary,
    onPrimary        = White,
    primaryContainer = GreenDark,
    onPrimaryContainer = GreenLight,
    secondary        = GreenLight,
    onSecondary      = GreenDark,
    background       = DarkBackground,
    onBackground     = White,
    surface          = DarkSurface,
    onSurface        = White,
    error            = ErrorRed,
    onError          = White
)

@Composable
fun NotePlusAdminTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else      -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}
