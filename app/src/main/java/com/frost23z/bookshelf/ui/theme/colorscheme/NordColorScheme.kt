package com.frost23z.bookshelf.ui.theme.colorscheme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Nord is an arctic, north-bluish color palette created by Arctic Ice Studio.
 * It is a set of sixteen colors with light and dark modes.
 * https://www.nordtheme.com/docs/colors-and-palettes
 */

internal object NordColorScheme : ColorSchemeController() {
    override val darkScheme =
        darkColorScheme(
            primary = Color(0xFF88C0D0), // nord8
            onPrimary = Color(0xFF2E3440), // nord0
            primaryContainer = Color(0xFF88C0D0), // nord8
            onPrimaryContainer = Color(0xFF2E3440), // nord0
            secondary = Color(0xFF81A1C1), // nord9 ok
            onSecondary = Color(0xFF2E3440), // nord0
            secondaryContainer = Color(0xFF4C566A), // nord3
            onSecondaryContainer = Color(0xFF88C0D0), // nord8
            tertiary = Color(0xFF5E81AC), // nord10
            onTertiary = Color(0xFF4c566a), // nord3
            tertiaryContainer = Color(0xFF5E81AC), // nord10
            onTertiaryContainer = Color(0xFFFFFFFF), // Pure white
            error = Color(0xFFBF616A), // nord11
            onError = Color(0xFF5D1521), // custom
            errorContainer = Color(0xFFBF616A), // nord11
            onErrorContainer = Color(0xFFECEFF4), // nord6
            background = Color(0xFF2E3440), // nord0
            onBackground = Color(0xFFECEFF4), // nord6
            surface = Color(0xFF2E3440), // nord0
            onSurface = Color(0xFFECEFF4), // nord6
            surfaceVariant = Color(0xFF3B4252), // nord1
            onSurfaceVariant = Color(0xFFD8DEE9), // nord4
            outline = Color(0xFF6B6F7B), // gray
            outlineVariant = Color(0xFF999999), // gray
            scrim = Color(0xFF2E3440), // nord0
            inverseSurface = Color(0xFFECEFF4), // nord6
            inverseOnSurface = Color(0xFF2E3440), // nord0
            inversePrimary = Color(0xFF397E91), // Muted nord8
            surfaceDim = Color(0xFF2E3440), // nord0
            surfaceBright = Color(0xFF434C5E), // nord2
            surfaceContainerLowest = Color(0xFF292E38), // darker nord0
            surfaceContainerLow = Color(0xFF2E3440), // nord0
            surfaceContainer = Color(0xFF3B4252), // nord1
            surfaceContainerHigh = Color(0xFF434C5E), // nord2
            surfaceContainerHighest = Color(0xFF4C566A), // nord3
        )

    override val lightScheme =
        lightColorScheme(
            primary = Color(0xFF5E81AC), // nord10
            onPrimary = Color(0xFFFFFFFF), // White
            primaryContainer = Color(0xFF81A1C1), // nord9
            onPrimaryContainer = Color(0xFF2E3440), // nord0
            secondary = Color(0xFF81A1C1), // nord9
            onSecondary = Color(0xFFFFFFFF), // White
            secondaryContainer = Color(0xFF8FBCBB), // nord7
            onSecondaryContainer = Color(0xFF2E3440), // nord0
            tertiary = Color(0xFF88C0D0), // nord8
            onTertiary = Color(0xFFFFFFFF), // White
            tertiaryContainer = Color(0xFF8FBCBB), // nord7
            onTertiaryContainer = Color(0xFF2E3440), // nord0
            error = Color(0xFFBF616A), // nord11
            onError = Color(0xFFFFFFFF), // White
            errorContainer = Color(0xFFD08770), // nord12
            onErrorContainer = Color(0xFF2E3440), // nord0
            background = Color(0xFFF8F9FA), // Lighter nord6
            onBackground = Color(0xFF2E3440), // nord0
            surface = Color(0xFFFCF8F9), // Lightest
            onSurface = Color(0xFF2E3440), // nord0
            surfaceVariant = Color(0xFFE5E9F0), // nord5
            onSurfaceVariant = Color(0xFF434C5E), // nord2
            outline = Color(0xFF4C566A), // nord3
            outlineVariant = Color(0xFFD8DEE9), // nord4
            scrim = Color(0xFF2E3440), // nord0
            inverseSurface = Color(0xFF3B4252), // nord1
            inverseOnSurface = Color(0xFFECEFF4), // nord6
            inversePrimary = Color(0xFF88C0D0), // nord8
            surfaceDim = Color(0xFFE5E9F0), // nord5
            surfaceBright = Color(0xFFFCF8F9), // Lightest
            surfaceContainerLowest = Color(0xFFFFFFFF), // Pure white
            surfaceContainerLow = Color(0xFFF5F7FA), // Lighter nord6
            surfaceContainer = Color(0xFFECEFF4), // nord6
            surfaceContainerHigh = Color(0xFFE5E9F0), // nord5
            surfaceContainerHighest = Color(0xFFD8DEE9), // nord4
        )
}