package com.frost23z.bookshelf.ui.more.settings.userinterface

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Languages
import com.frost23z.bookshelf.preferences.UISettingsPreference
import com.frost23z.bookshelf.ui.theme.Theme
import com.frost23z.bookshelf.ui.theme.ThemeMode
import com.frost23z.bookshelf.ui.theme.ThemeProperties
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UISettingsScreenModel(
    val uiSettingsPreference: UISettingsPreference
) : StateScreenModel<UISettingsScreenModel.State>(State()) {
    data class State(
        val themeProperties: ThemeProperties =
            ThemeProperties(
                theme = Theme.DYNAMIC,
                themeMode = ThemeMode.SYSTEM,
                isAmoledDark = false
            ),
        val language: Languages = Languages.ENGLISH,
        val showLanguageDialog: Boolean = false
    )

    init {
        screenModelScope.launch {
            uiSettingsPreference.getThemeProperties().collect {
                mutableState.update { state -> state.copy(themeProperties = it) }
            }
        }

        screenModelScope.launch {
            uiSettingsPreference.getLanguage().collect {
                mutableState.update { state -> state.copy(language = it) }
            }
        }
    }

    fun updateTheme(theme: Theme) {
        screenModelScope.launch {
            uiSettingsPreference.setTheme(theme)
        }
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        screenModelScope.launch {
            uiSettingsPreference.setThemeMode(themeMode)
        }
    }

    fun toggleAmoledDarkMode(isAmoled: Boolean) {
        screenModelScope.launch {
            uiSettingsPreference.setThemeDarkAmoled(isAmoled)
        }
    }

    fun updateLanguage(language: Languages) {
        screenModelScope.launch {
            uiSettingsPreference.setLanguage(language)
            mutableState.update { state -> state.copy(language = language) }
        }
    }

    fun toggleLanguageMenu() {
        mutableState.update { state -> state.copy(showLanguageDialog = !state.showLanguageDialog) }
    }
}