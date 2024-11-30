package com.frost23z.bookshelf.ui.addedit.components.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.components.IconTextButtonVertical
import com.frost23z.bookshelf.ui.core.constants.MediumIcon
import com.frost23z.bookshelf.ui.core.constants.PaddingExtraSmall

@Composable
fun ImagePickDialog(
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onPickFromGallery: () -> Unit,
    onSelectUrl: () -> Unit,
    iconSize: Dp = 36.dp,
    tint: Color = LocalContentColor.current
) {
    AlertDialog(onDismissRequest = onDismiss, icon = {
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = "Select Image",
            modifier = Modifier.size(iconSize)
        )
    }, title = {
        Text(
            text = "Select Image",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }, text = {
        ImagePickDialogOptions(
            onPickFromGallery = onPickFromGallery,
            onTakePhoto = onTakePhoto,
            onUrlOptionSelected = onSelectUrl,
            tint = tint
        )
    }, confirmButton = {}, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = "Cancel")
        }
    })
}

@Composable
fun ImagePickDialogOptions(
    onPickFromGallery: () -> Unit,
    onTakePhoto: () -> Unit,
    onUrlOptionSelected: () -> Unit,
    tint: Color = LocalContentColor.current
) {
    val items =
        listOf(
            IconTextButtonVerticalItem(
                icon = Icons.Default.CameraAlt,
                onClick = onTakePhoto,
                iconDescription = "Take Photo",
                text = "Camera"
            ),
            IconTextButtonVerticalItem(
                icon = Icons.Default.Photo,
                onClick = onPickFromGallery,
                iconDescription = "Pick from Gallery",
                text = "Gallery"
            ),
            IconTextButtonVerticalItem(
                icon = Icons.Default.Explore,
                onClick = onUrlOptionSelected,
                iconDescription = "Enter URL",
                text = "URL"
            )
        )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items.forEach { (icon, onclick, iconDescription, text) ->
            item {
                IconTextButtonVertical(
                    icon = icon,
                    onClick = onclick,
                    modifier = Modifier.aspectRatio(1f),
                    iconDescription = iconDescription,
                    iconSize = MediumIcon,
                    text = text,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    tint = tint,
                    spacing = PaddingExtraSmall
                )
            }
        }
    }
}

data class IconTextButtonVerticalItem(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val iconDescription: String? = null,
    val text: String
)
