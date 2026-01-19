package com.sonalisulgadle.taskmanagerpro.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = DarkIndigoPrimary,
    onPrimary = DarkOnPrimary,

    secondary = DarkMintSecondary,
    onSecondary = DarkOnSecondary,

    background = DarkBackground,
    onBackground = DarkOnBackground,

    surface = DarkSurface,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = IndigoPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = IndigoPrimaryLight,

    secondary = MintSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = MintSecondaryLight,

    background = LightBackground,
    onBackground = LightOnBackground,

    surface = LightSurface,
    onSurface = LightOnSurface
)

// --- Typography (minimal, readable) ---
private val AppTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 20.sp,
        letterSpacing = 0.15f.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp
    )
)

@Composable
fun TaskManagerProTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = Shapes(
            small = RoundedCornerShape(8),
            medium = RoundedCornerShape(12),
            large = RoundedCornerShape(16)
        ),
        content = content
    )
}
