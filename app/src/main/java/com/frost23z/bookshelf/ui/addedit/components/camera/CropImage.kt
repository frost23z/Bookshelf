package com.frost23z.bookshelf.ui.addedit.components.camera

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.yalantis.ucrop.UCrop
import java.io.File

@Composable
fun CropImage(
	imageUri: Uri,
	croppedImageUri: (Uri) -> Unit
) {
	val context = LocalContext.current
	val tempFile = File.createTempFile(
		"temp_image_${System.currentTimeMillis()}",
		".jpg",
		context.cacheDir
	)
	val tempUri = Uri.fromFile(tempFile)
	val cropIntent = UCrop
		.of(imageUri, tempUri)
		.withAspectRatio(2f, 3f)
		.withMaxResultSize(400, 600)
		.getIntent(context)

	val cropResultLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.StartActivityForResult()
	) { result: ActivityResult ->
		when (result.resultCode) {
			Activity.RESULT_OK -> {
				val croppedUri = UCrop.getOutput(result.data!!)
				croppedImageUri(croppedUri!!)
			}
			UCrop.RESULT_ERROR -> {
				val cropError = UCrop.getError(result.data!!)
				cropError?.printStackTrace()
			}
		}
	}

	LaunchedEffect(tempUri) {
		cropResultLauncher.launch(cropIntent)
	}
}