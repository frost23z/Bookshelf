package com.frost23z.bookshelf.ui.addedit.components.camera

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
	controller: LifecycleCameraController,
	modifier: Modifier = Modifier
) {
	val lifecycleOwner = LocalLifecycleOwner.current
	AndroidView(
		factory = {
			PreviewView(it).apply {
				this.controller = controller
				controller.bindToLifecycle(lifecycleOwner)
				controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
			}
		},
		modifier = modifier
	)
}