package com.frost23z.bookshelf.ui.addedit.components.camera

import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.constants.IconSize
import java.io.File

@Composable
fun CameraScreen(onImageCaptured: (Uri) -> Unit) {
	val context = LocalContext.current
	val controller = remember { LifecycleCameraController(context).apply { setEnabledUseCases(CameraController.IMAGE_CAPTURE) } }

	Scaffold(
		floatingActionButton = {
			IconButton(icon = Icons.Outlined.Camera, onClick = {
				captureImage(controller, context = context, onCaptured = { uri -> onImageCaptured(uri) })
			}, iconSize = IconSize.XXLarge)
		},
		floatingActionButtonPosition = FabPosition.Center
	) { innerPadding ->
		Box(modifier = Modifier.padding(innerPadding)) {
			CameraPreview(controller, Modifier.fillMaxSize())
		}
	}

	// TODO: Need to make proper permission handling
	val permissionLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestPermission(),
		onResult = { isGranted ->
			if (!isGranted) {
				Log.e("CameraScreen", "Permission denied")
			}
		}
	)

	LaunchedEffect(Unit) {
		permissionLauncher.launch(Manifest.permission.CAMERA)
	}

	DisposableEffect(controller) {
		onDispose {
			controller.unbind()
		}
	}
}

private fun captureImage(
	controller: LifecycleCameraController,
	onCaptured: (Uri) -> Unit,
	onError: (ImageCaptureException) -> Unit = {},
	context: Context
) {
	val tempFile = File.createTempFile(
		"temp_image_${System.currentTimeMillis()}",
		".jpg",
		context.cacheDir
	)

	val outputOptions = ImageCapture.OutputFileOptions.Builder(tempFile).build()

	controller.takePicture(
		outputOptions,
		ContextCompat.getMainExecutor(context),
		object : ImageCapture.OnImageSavedCallback {
			override fun onImageSaved(output: ImageCapture.OutputFileResults) {
				onCaptured(Uri.fromFile(tempFile))
			}

			override fun onError(exception: ImageCaptureException) {
				Log.e("CameraScreen", "Image capture failed", exception)
				onError(exception)
			}
		}
	)
}