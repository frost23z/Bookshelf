package com.frost23z.bookshelf.ui.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class ThemePreference(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("selected_theme")
        private val THEME_MODE = intPreferencesKey("theme_mode")
        private val THEME_DARK_AMOLED = booleanPreferencesKey("theme_dark_amoled")
    }

    private fun getTheme(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: "default"
        }
    }

    suspend fun setTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    private fun getThemeMode(): Flow<ThemeMode> {
        return dataStore.data.map { preferences ->
            val modeOrdinal = preferences[THEME_MODE] ?: ThemeMode.SYSTEM.ordinal
            ThemeMode.entries.getOrNull(modeOrdinal) ?: ThemeMode.SYSTEM
        }
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = themeMode.ordinal
        }
    }

    private fun getThemeDarkAmoled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_DARK_AMOLED] ?: false
        }
    }

    suspend fun setThemeDarkAmoled(isAmoled: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_DARK_AMOLED] = isAmoled
        }
    }

    fun getThemeProperties(): Flow<ThemeProperties> =
        combine(getTheme(), getThemeMode(), getThemeDarkAmoled()) { theme, mode, isAmoled ->
            ThemeProperties(
                themeName = theme,
                themeMode = mode,
                isAmoledDark = isAmoled
            )
        }
}