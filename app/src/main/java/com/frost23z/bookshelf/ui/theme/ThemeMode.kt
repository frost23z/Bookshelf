package com.frost23z.bookshelf.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.frost23z.bookshelf.ui.theme.colorscheme.DynamicColorScheme
import com.frost23z.bookshelf.ui.theme.colorscheme.NordColorScheme

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

enum class Theme {
    DYNAMIC,
    NORD
}

data class ThemeProperties(
    val theme: Theme,
    val themeMode: ThemeMode,
    val isDark: Boolean = isDarkMode(themeMode),
    val isAmoledDark: Boolean
)

fun isDarkMode(themeMode: ThemeMode): Boolean {
    return when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> false
    }
}

@Composable
fun themeColorMapping() =
    mapOf(
        Theme.DYNAMIC to DynamicColorScheme(context = LocalContext.current),
        Theme.NORD to NordColorScheme
    )