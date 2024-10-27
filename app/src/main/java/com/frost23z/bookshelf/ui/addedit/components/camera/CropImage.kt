package com.frost23z.bookshelf.ui.addedit.components.camera

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

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
        val imgCacheDir = File(context.cacheDir, "temp_images").apply {
            if (!exists()) {
                mkdirs()
            }
        }
        val name = "Cropped_${System.currentTimeMillis()}.jpg"
        val destinationUri =
            Uri.fromFile(File(imgCacheDir, name))
        val cropIntent =
            UCrop.of(imageUri, destinationUri).withAspectRatio(2f, 3f)
                .withMaxResultSize(400, 600).getIntent(context)
        cropResultLauncher.launch(cropIntent)
    }
}

fun moveImageToCoverFolder(context: Context, cachedUri: Uri): Uri? {
    val coverDir = File(context.filesDir, "cover").apply {
        if (!exists()) mkdirs()
    }
    val coverFile = File(coverDir, "Cover_${System.currentTimeMillis()}.jpg")

    return try {
        val sourceFile = File(cachedUri.path ?: throw IOException("Invalid URI path"))
        sourceFile.copyTo(coverFile, overwrite = true)
        Uri.fromFile(coverFile)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}