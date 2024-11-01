package com.frost23z.bookshelf

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.Navigator
import com.frost23z.bookshelf.ui.home.HomeScreen
import com.frost23z.bookshelf.ui.theme.BookshelfTheme

class MainActivity : ComponentActivity() {
    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                print("Camera permission granted")
            } else {
                print("Camera permission denied")
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
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
            BookshelfTheme {
                Navigator(HomeScreen)
            }
        }
    }
}