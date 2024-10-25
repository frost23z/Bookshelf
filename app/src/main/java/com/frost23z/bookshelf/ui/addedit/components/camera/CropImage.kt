package com.frost23z.bookshelf.ui.addedit.components.camera

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.yalantis.ucrop.UCrop
import java.io.File

@Composable
fun CropImage(
    context: Context, imageUri: Uri, onImageCropped: (Uri?) -> Unit
) {
    val cropResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val croppedUri = UCrop.getOutput(result.data!!)
            onImageCropped(croppedUri)
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            cropError?.printStackTrace()
        }
    }

    LaunchedEffect(imageUri) {
        val destinationUri =
            Uri.fromFile(File(context.cacheDir, "Cropped_${System.currentTimeMillis()}.jpg"))
        val cropIntent =
            UCrop.of(imageUri, destinationUri).withAspectRatio(2f, 3f)
                .withMaxResultSize(maxHeight, maxWidth).getIntent(context)
        cropResultLauncher.launch(cropIntent)
    }
}