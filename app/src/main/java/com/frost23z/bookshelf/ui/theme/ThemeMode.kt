package com.frost23z.bookshelf.ui.theme

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

data class ThemeProperties(
    val themeName: String,
    val themeMode: ThemeMode,
    val isAmoledDark: Boolean
)