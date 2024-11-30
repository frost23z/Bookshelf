package com.frost23z.bookshelf.ui.theme.colorscheme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

abstract class ColorSchemeController {
    abstract val darkScheme: ColorScheme
    abstract val lightScheme: ColorScheme

    fun getColorScheme(
        isDark: Boolean,
        isAmoled: Boolean = false
    ): ColorScheme {
        return when {
            !isDark -> lightScheme
            !isAmoled -> darkScheme
            else -> createAmoledScheme()
        }
    }

    private fun createAmoledScheme(): ColorScheme {
        return darkScheme.copy(
            background = Color.Black,
            onBackground = Color.White,
            surface = Color.Black,
            onSurface = Color.White,
            surfaceVariant = Color(0xFF131313),
            surfaceContainerLowest = Color(0xFF151515),
            surfaceContainerLow = Color(0xFF171717),
            surfaceContainer = Color(0xFF191919),
            surfaceContainerHigh = Color(0xFF1B1B1B),
            surfaceContainerHighest = Color(0xFF1D1D1D),
        )
    }
}