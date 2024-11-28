package com.frost23z.bookshelf.ui.more

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost23z.bookshelf.ui.theme.ThemeMode
import com.frost23z.bookshelf.ui.theme.ThemePreference
import com.frost23z.bookshelf.ui.theme.ThemeProperties
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val themePreference: ThemePreference
) : ViewModel() {
    // Expose theme configuration as StateFlow
    val themeProperties =
        themePreference
            .getThemeProperties()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue =
                    ThemeProperties(
                        themeName = "default",
                        themeMode = ThemeMode.SYSTEM,
                        isAmoledDark = false
                    )
            )

    fun updateTheme(themeName: String) {
        viewModelScope.launch {
            themePreference.setTheme(themeName)
            refreshThemeConfig()
        }
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            themePreference.setThemeMode(themeMode)
            refreshThemeConfig()
        }
    }

    fun toggleAmoledDarkMode(isAmoled: Boolean) {
        viewModelScope.launch {
            themePreference.setThemeDarkAmoled(isAmoled)
            refreshThemeConfig()
        }
    }

    private fun refreshThemeConfig() {
        viewModelScope.launch {
            themePreference.getThemeProperties().collect {
                Log.d("ThemeViewModel", "Theme config refreshed: $it")
                // This will trigger recomposition in the UI
            }
        }
    }
}