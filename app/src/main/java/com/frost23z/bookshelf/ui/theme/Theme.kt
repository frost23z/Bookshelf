package com.frost23z.bookshelf.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.frost23z.bookshelf.ui.theme.colorscheme.DynamicColorScheme

@Composable
fun AppTheme(
    themeProperties: ThemeProperties,
    content: @Composable () -> Unit
) {
    val colorScheme = getThemeColorScheme(themeProperties)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun getThemeColorScheme(themeProperties: ThemeProperties): ColorScheme = resolveColorScheme(
    theme = themeProperties.theme,
    isDark = themeProperties.isDark,
    isAmoled = themeProperties.isAmoledDark
)

@Composable
fun resolveColorScheme(
    theme: Theme,
    isDark: Boolean,
    isAmoled: Boolean
): ColorScheme {
    val context = LocalContext.current
    val colorSchemeProvider =
        themeColorMapping().getOrDefault(
            theme,
            DynamicColorScheme(context = context)
        )
    return colorSchemeProvider.getColorScheme(
        isDark = isDark,
        isAmoled = isAmoled
    )
}