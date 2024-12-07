package com.frost23z.bookshelf

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.Navigator
import com.frost23z.bookshelf.data.Languages
import com.frost23z.bookshelf.preferences.UISettingsPreference
import com.frost23z.bookshelf.ui.core.components.LocaleWrapper
import com.frost23z.bookshelf.ui.core.util.SnackbarEventObserver
import com.frost23z.bookshelf.ui.home.HomeScreen
import com.frost23z.bookshelf.ui.theme.AppTheme
import com.frost23z.bookshelf.ui.theme.ThemeProperties
import com.frost23z.bookshelf.ui.theme.isDarkMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.get
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                print("Camera permission granted")
            } else {
                print("Camera permission denied")
            }
        }

    override fun attachBaseContext(newBase: Context) {
        val configuration = newBase.resources.configuration
        val uiSettingsPreference: UISettingsPreference = get()

        // Handle locale
        val language = runBlocking { uiSettingsPreference.getLanguage().first() }
        val locale = Locale(language.code)
        Locale.setDefault(locale)
        configuration.setLocales(LocaleList(locale))

        // Handle theme mode
        val themeProperties = runBlocking { uiSettingsPreference.getThemeProperties().first() }

        configuration.uiMode =
            if (themeProperties.isDark) {
                configuration.uiMode or Configuration.UI_MODE_NIGHT_YES
            } else {
                configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv() or Configuration.UI_MODE_NIGHT_NO
            }

        val context = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionRequest.launch(Manifest.permission.CAMERA)
        }
        enableEdgeToEdge()
        setContent {
            val uiSettingsPreference: UISettingsPreference = get()
            var themeProperties by remember { mutableStateOf<ThemeProperties?>(null) }
            var language by remember { mutableStateOf(Languages.ENGLISH) }

            LaunchedEffect(Unit) {
                launch {
                    uiSettingsPreference.getThemeProperties().collect { properties ->
                        themeProperties = properties
                    }
                }
                launch {
                    uiSettingsPreference.getLanguage().collect { newLanguage ->
                        language = newLanguage
                    }
                }
            }

            if (themeProperties == null) return@setContent

            LocaleWrapper(language = language) {
                AppTheme(themeProperties!!) {
                    val snackbarHostState = remember { SnackbarHostState() }
                    SnackbarEventObserver(snackbarHostState = snackbarHostState)
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackbarHostState,
                                modifier = Modifier.padding(bottom = 72.dp)
                            )
                        }
                    ) { innerPadding ->
                        Navigator(HomeScreen)
                    }
                }
            }
        }
    }
}