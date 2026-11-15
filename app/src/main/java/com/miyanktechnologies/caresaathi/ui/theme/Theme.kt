package com.miyanktechnologies.caresaathi.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryNavy,
    secondary = SecondaryTeal,
    background = AppBackground,
    surface = CardBackground,
    onPrimary = CardBackground,
    onSecondary = CardBackground,
    onBackground = PrimaryText,
    onSurface = PrimaryText,
    error = DangerRed
)

private val HighContrastLightColorScheme = lightColorScheme(
    primary = HighContrastBlack,
    secondary = HighContrastBlack,
    background = HighContrastWhite,
    surface = HighContrastWhite,
    onPrimary = HighContrastWhite,
    onSecondary = HighContrastWhite,
    onBackground = HighContrastBlack,
    onSurface = HighContrastBlack,
    error = DangerRed
)

@Composable
fun CareSaathiTheme(
    darkTheme: Boolean = false,
    largeText: Boolean = false,
    extraLargeText: Boolean = false,
    highContrast: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        highContrast -> HighContrastLightColorScheme
        else -> LightColorScheme
    }

    val typography = getCareSaathiTypography(largeText, extraLargeText)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme && !highContrast
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
