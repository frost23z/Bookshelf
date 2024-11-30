package com.frost23z.bookshelf.ui.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.frost23z.bookshelf.ui.core.helpers.updateGlobalConfiguration
import com.frost23z.bookshelf.ui.theme.colorscheme.DynamicColorScheme

val LocalContextProvider =
    staticCompositionLocalOf<Context> {
        error("No themed context provided")
    }

@Composable
fun AppTheme(
    themeProperties: ThemeProperties,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val updatedContext = context.updateGlobalConfiguration(themeProperties.isDark)
    val colorScheme = getThemeColorScheme(themeProperties)

    CompositionLocalProvider(LocalContextProvider provides updatedContext) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun getThemeColorScheme(themeProperties: ThemeProperties): ColorScheme {
    return resolveColorScheme(
        theme = themeProperties.theme,
        isDark = themeProperties.isDark,
        isAmoled = themeProperties.isAmoledDark
    )
}

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