package com.frost23z.bookshelf.ui.addedit.components.camera

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ImagePicker(onImagePicked: (Uri) -> Unit) {
	val imagePickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickVisualMedia(),
		onResult = { uri ->
			uri?.let { onImagePicked(it) }
		}
	)
	LaunchedEffect("image picker") {
		imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
	}
}