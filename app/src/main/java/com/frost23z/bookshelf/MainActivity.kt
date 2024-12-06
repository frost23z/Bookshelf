package com.frost23z.bookshelf

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.frost23z.bookshelf.ui.core.util.SnackbarEventObserver
import com.frost23z.bookshelf.ui.home.HomeScreen
import com.frost23z.bookshelf.ui.theme.AppTheme
import com.frost23z.bookshelf.preferences.AppearancePreference
import com.frost23z.bookshelf.ui.theme.ThemeProperties
import com.frost23z.bookshelf.utils.LocaleUtils
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                print("Camera permission granted")
            } else {
                print("Camera permission denied")
            }
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
            val appearancePreference: AppearancePreference = get()
            var themeProperties by remember { mutableStateOf<ThemeProperties?>(null) }

            LaunchedEffect(Unit) {
                appearancePreference.getThemeProperties().collect { properties ->
                    themeProperties = properties
                }
                appearancePreference.getLanguage().collect { language ->
                    LocaleUtils.setLocale(this@MainActivity, language)
                }
            }

            if (themeProperties == null) return@setContent

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