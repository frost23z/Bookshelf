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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.ui.addedit.components.camera.CameraScreen
import com.frost23z.bookshelf.ui.addedit.components.camera.CropImage
import com.frost23z.bookshelf.ui.addedit.components.core.ImagePickDialog
import com.frost23z.bookshelf.ui.addedit.components.core.ImageUrlInputDialog

enum class CoverState {
    NONE,
    IMAGE_PICKER,
    CAMERA,
    GALLERY,
    URL_INPUT,
    CROP
}

@Composable
fun CoverSection(
    coverUri: String?,
    onCoverUriChange: (Uri?) -> Unit,
    navigator: Navigator
) {
    val context = LocalContext.current
    var coverState by rememberSaveable { mutableStateOf(CoverState.NONE) }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

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
                    rememberVectorPainter(Icons.Default.Image)
                },
            contentDescription = if (coverUri != null) "Selected cover image" else "Placeholder for book cover",
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(100.dp, 150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { coverState = CoverState.IMAGE_PICKER },
            colorFilter = if (coverUri == null) ColorFilter.tint(MaterialTheme.colorScheme.primary) else null
        )

        if (coverUri == null) {
            TextButton(
                onClick = { coverState = CoverState.IMAGE_PICKER },
            ) {
                Text(text = "Select Image")
            }
        } else {
            Row {
                TextButton(
                    onClick = { coverState = CoverState.IMAGE_PICKER },
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

    when (coverState) {
        CoverState.NONE -> {}
        CoverState.IMAGE_PICKER -> {
            ImagePickDialog(
                onDismiss = { coverState = CoverState.NONE },
                onTakePhoto = {
                    coverState = CoverState.CAMERA
                },
                onPickFromGallery = {
                    coverState = CoverState.GALLERY
                },
                onSelectUrl = {
                    coverState = CoverState.URL_INPUT
                }
            )
        }

        CoverState.CAMERA -> {
            navigator.push(
                CameraScreen(onImageCaptured = { uri ->
                    selectedImageUri = uri
                    navigator.pop()
                    coverState = CoverState.CROP
                }, onError = { error ->
                    Log.e("CoverSection", "Camera error: ${error.message}")
                    error.printStackTrace()
                })
            )
        }

        CoverState.GALLERY -> {
            // TODO: Implement gallery image picker
        }

        CoverState.URL_INPUT -> {
            ImageUrlInputDialog(
                onDismiss = { coverState = CoverState.NONE },
                onUriEntered = { enteredUrl ->
                    selectedImageUri = Uri.parse(enteredUrl)
                    coverState = CoverState.CROP
                }
            )
        }

        CoverState.CROP -> {
            selectedImageUri?.let { uri ->
                CropImage(
                    context = context,
                    imageUri = uri,
                    onImageCropped = { croppedUri ->
                        onCoverUriChange(croppedUri)
                        selectedImageUri = null
                        coverState = CoverState.NONE
                    }
                )
            }
        }
    }
}