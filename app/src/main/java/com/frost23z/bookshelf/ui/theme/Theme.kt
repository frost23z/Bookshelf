package com.frost23z.bookshelf.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFAAC7FF),
    onPrimary = Color(0xFF002F64),
    primaryContainer = Color(0xFF6E94D6),
    onPrimaryContainer = Color(0xFF000000),
    secondary = Color(0xFFB9C7E5),
    onSecondary = Color(0xFF233148),
    secondaryContainer = Color(0xFF323F58),
    onSecondaryContainer = Color(0xFFC6D4F3),
    tertiary = Color(0xFFF2B0F5),
    onTertiary = Color(0xFF4D1A55),
    tertiaryContainer = Color(0xFFBC7FC0),
    onTertiaryContainer = Color(0xFF000000),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF121317),
    onBackground = Color(0xFFE2E2E8),
    surface = Color(0xFF121317),
    onSurface = Color(0xFFE2E2E8),
    surfaceVariant = Color(0xFF434750),
    onSurfaceVariant = Color(0xFFC3C6D2),
    outline = Color(0xFF8D909B),
    outlineVariant = Color(0xFF434750),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFFE2E2E8),
    inverseOnSurface = Color(0xFF2F3035),
    inversePrimary = Color(0xFF365E9D),
    surfaceDim = Color(0xFF121317),
    surfaceBright = Color(0xFF38393E),
    surfaceContainerLowest = Color(0xFF0C0E12),
    surfaceContainerLow = Color(0xFF1A1C20),
    surfaceContainer = Color(0xFF1E2024),
    surfaceContainerHigh = Color(0xFF282A2E),
    surfaceContainerHighest = Color(0xFF333539)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF365E9D),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF82A8EC),
    onPrimaryContainer = Color(0xFF001C40),
    secondary = Color(0xFF515F79),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD9E4FF),
    onSecondaryContainer = Color(0xFF3C4962),
    tertiary = Color(0xFF814A87),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD091D4),
    onTertiaryContainer = Color(0xFF36013F),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF9F9FF),
    onBackground = Color(0xFF1A1C20),
    surface = Color(0xFFF9F9FF),
    onSurface = Color(0xFF1A1C20),
    surfaceVariant = Color(0xFFDFE2EE),
    onSurfaceVariant = Color(0xFF434750),
    outline = Color(0xFF737781),
    outlineVariant = Color(0xFFC3C6D2),
    scrim = Color(0xFF000000),
    inverseSurface = Color(0xFF2F3035),
    inverseOnSurface = Color(0xFFF1F0F6),
    inversePrimary = Color(0xFFAAC7FF),
    surfaceDim = Color(0xFFDAD9DF),
    surfaceBright = Color(0xFFF9F9FF),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF3F3F9),
    surfaceContainer = Color(0xFFEEEDF3),
    surfaceContainerHigh = Color(0xFFE8E7ED),
    surfaceContainerHighest = Color(0xFFE2E2E8)
)

@Composable
fun BookshelfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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
        typography = Typography,
        content = content
    )
}