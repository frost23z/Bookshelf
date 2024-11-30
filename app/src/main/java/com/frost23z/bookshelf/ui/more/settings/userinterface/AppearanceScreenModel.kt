package com.frost23z.bookshelf.ui.more.settings.userinterface

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.ui.theme.Theme
import com.frost23z.bookshelf.ui.theme.ThemeMode
import com.frost23z.bookshelf.ui.theme.ThemePreference
import com.frost23z.bookshelf.ui.theme.ThemeProperties
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppearanceScreenModel(
    val themePreference: ThemePreference
) : StateScreenModel<AppearanceScreenModel.State>(State()) {
    data class State(
        val themeProperties: ThemeProperties =
            ThemeProperties(
                theme = Theme.DYNAMIC,
                themeMode = ThemeMode.SYSTEM,
                isAmoledDark = false
            )
    )

    init {
        screenModelScope.launch {
            themePreference.getThemeProperties().collect {
                mutableState.update { state -> state.copy(themeProperties = it) }
            }
        }
    }

    fun updateTheme(theme: Theme) {
        screenModelScope.launch {
            themePreference.setTheme(theme)
        }
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        screenModelScope.launch {
            themePreference.setThemeMode(themeMode)
        }
    }

    fun toggleAmoledDarkMode(isAmoled: Boolean) {
        screenModelScope.launch {
            themePreference.setThemeDarkAmoled(isAmoled)
        }
    }
}