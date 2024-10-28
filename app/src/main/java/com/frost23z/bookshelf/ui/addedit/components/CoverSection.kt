package com.frost23z.bookshelf.ui.addedit.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.addedit.components.camera.CameraScreen
import com.frost23z.bookshelf.ui.addedit.components.camera.CropImage
import com.frost23z.bookshelf.ui.addedit.components.core.ImagePickDialog
import com.frost23z.bookshelf.ui.addedit.components.core.ImageUrlInputDialog

@Composable
fun CoverSection(
    coverUri: Uri?,
    onCoverUriChange: (Uri?) -> Unit,
    navigator: Navigator
) {
    val context = LocalContext.current
    var showImagePickerDialog by rememberSaveable { mutableStateOf(false) }
    var crop by rememberSaveable { mutableStateOf(false) }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var showUrlInputDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter =
                if (coverUri != null) {
                    rememberAsyncImagePainter(
                        model = coverUri
                    )
                } else {
                    painterResource(id = R.drawable.ic_launcher_foreground)
                },
            contentDescription = "Select Image",
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(100.dp, 150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { showImagePickerDialog = true },
            colorFilter = if (coverUri == null) ColorFilter.tint(MaterialTheme.colorScheme.primary) else null
        )

        when (coverUri) {
            null -> {
                TextButton(
                    onClick = { showImagePickerDialog = true },
                ) {
                    Text(text = "Select Image")
                }
            }

            else -> {
                Row {
                    TextButton(
                        onClick = { showImagePickerDialog = true },
                    ) {
                        Text(text = "Change Image")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = { onCoverUriChange(null) },
                    ) {
                        Text(text = "Remove Image")
                    }
                }
            }
        }

        if (showImagePickerDialog) {
            ImagePickDialog(onDismiss = { showImagePickerDialog = false }, onTakePhoto = {
                showImagePickerDialog = false
                navigator.push(
                    CameraScreen(onImageCaptured = { uri ->
                        selectedImageUri = uri
                        navigator.pop()
                        crop = true
                    }, onError = { error ->
                        Log.e("CoverSection", "Camera error: ${error.message}")
                        error.printStackTrace()
                    })
                )
            }, onPickFromGallery = {
                showImagePickerDialog = false
            }, onSelectUrl = {
                showImagePickerDialog = false
                showUrlInputDialog = true
            })
        }
        if (crop && selectedImageUri != null) {
            CropImage(
                context = context,
                imageUri = selectedImageUri!!,
                onImageCropped = { uri ->
                    onCoverUriChange(uri)
                    selectedImageUri = null
                    crop = false
                }
            )
        }
        if (showUrlInputDialog) {
            ImageUrlInputDialog(
                onDismiss = { showUrlInputDialog = false },
                onUriEntered = { enteredUrl ->
                    selectedImageUri = Uri.parse(enteredUrl)
                    showUrlInputDialog = false
                    crop = true
                }
            )
        }
    }
}