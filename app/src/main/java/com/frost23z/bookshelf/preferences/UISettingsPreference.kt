package com.frost23z.bookshelf.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.frost23z.bookshelf.data.Languages
import com.frost23z.bookshelf.ui.theme.Theme
import com.frost23z.bookshelf.ui.theme.ThemeMode
import com.frost23z.bookshelf.ui.theme.ThemeProperties
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class UISettingsPreference(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val THEME_KEY = intPreferencesKey("theme")
        private val THEME_MODE = intPreferencesKey("theme_mode")
        private val THEME_DARK_AMOLED = booleanPreferencesKey("theme_dark_amoled")
        private val LANGUAGE = intPreferencesKey("language")
    }

    private fun getTheme(): Flow<Theme> = dataStore.data.map { preferences ->
        val themeOrdinal = preferences[THEME_KEY] ?: Theme.DYNAMIC.ordinal
        Theme.entries.getOrNull(themeOrdinal) ?: Theme.DYNAMIC
    }

    suspend fun setTheme(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.ordinal
        }
    }

    private fun getThemeMode(): Flow<ThemeMode> = dataStore.data.map { preferences ->
        val modeOrdinal = preferences[THEME_MODE] ?: ThemeMode.SYSTEM.ordinal
        ThemeMode.entries.getOrNull(modeOrdinal) ?: ThemeMode.SYSTEM
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.ordinal
        }
    }

    private fun getThemeDarkAmoled(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[THEME_DARK_AMOLED] ?: false
    }

    suspend fun setThemeDarkAmoled(isAmoled: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_DARK_AMOLED] = isAmoled
        }
    }

    fun getThemeProperties(): Flow<ThemeProperties> = combine(getTheme(), getThemeMode(), getThemeDarkAmoled()) { theme, mode, isAmoled ->
        ThemeProperties(
            theme = theme,
            themeMode = mode,
            isAmoledDark = isAmoled
        )
    }

    fun getLanguage(): Flow<Languages> = dataStore.data.map { preferences ->
        val languageOrdinal = preferences[LANGUAGE] ?: Languages.ENGLISH.ordinal
        Languages.entries.getOrNull(languageOrdinal) ?: Languages.ENGLISH
    }

    suspend fun setLanguage(language: Languages) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language.ordinal
        }
    }
}