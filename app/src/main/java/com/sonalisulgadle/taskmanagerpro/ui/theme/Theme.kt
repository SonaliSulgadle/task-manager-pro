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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFCBC5EA),
    onPrimary = Color(0xFF183642),

    secondary = Color(0xFF73628A),
    onSecondary = Color.White,

    background = Color(0xFF183642),
    onBackground = Color(0xFFEAEAEA),

    surface = Color(0xFF313D5A),
    onSurface = Color(0xFFEAEAEA),

    outline = Color(0xFF73628A)
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF73628A),
    onPrimary = Color.White,

    secondary = Color(0xFFCBC5EA),
    onSecondary = Color(0xFF313D5A),

    background = Color(0xFFEAEAEA),
    onBackground = Color(0xFF183642),

    surface = Color.White,
    onSurface = Color(0xFF313D5A),

    outline = Color(0xFFCBC5EA)
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
