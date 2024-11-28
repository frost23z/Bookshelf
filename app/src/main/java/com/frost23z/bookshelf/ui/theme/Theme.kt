package com.frost23z.bookshelf.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.frost23z.bookshelf.ui.theme.colorscheme.DynamicColorScheme
import com.frost23z.bookshelf.ui.theme.colorscheme.NordColorScheme

@Composable
fun AppTheme(
    themeProperties: ThemeProperties,
    content: @Composable () -> Unit
) {
    val isDarkTheme =
        when (themeProperties.themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
        }

    val context = LocalContext.current

    val colorScheme =
        when (themeProperties.themeName) {
            "dynamic" ->
                DynamicColorScheme(context).getColorScheme(
                    isDark = isDarkTheme,
                    isAmoled = themeProperties.isAmoledDark
                )
            "nord" ->
                NordColorScheme.getColorScheme(
                    isDark = isDarkTheme,
                    isAmoled = themeProperties.isAmoledDark
                )
            else ->
                NordColorScheme.getColorScheme(
                    isDark = isDarkTheme,
                    isAmoled = themeProperties.isAmoledDark
                )
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}