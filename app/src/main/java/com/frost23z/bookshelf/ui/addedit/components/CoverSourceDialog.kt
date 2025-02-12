package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.components.AlertDialog
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.constants.IconSize

@Composable
fun CoverSourceDialog(
	onDismissRequest: () -> Unit,
	onPickFromGallery: () -> Unit,
	onTakePhoto: () -> Unit,
	onUrlOptionSelected: () -> Unit,
	modifier: Modifier = Modifier
) {
	AlertDialog(
		icon = Icons.Outlined.AddAPhoto,
		iconSize = IconSize.Large,
		title = "Select cover source",
		onDismissRequest = onDismissRequest,
		modifier = modifier
	) {
		val coverSourceOptions = listOf(
			IconActionItem(
				icon = Icons.Default.CameraAlt,
				onClick = onTakePhoto,
				iconDescription = "Take Photo",
				text = "Camera"
			),
			IconActionItem(
				icon = Icons.Default.Photo,
				onClick = onPickFromGallery,
				iconDescription = "Pick from Gallery",
				text = "Gallery"
			),
			IconActionItem(
				icon = Icons.Default.Explore,
				onClick = onUrlOptionSelected,
				iconDescription = "Enter URL",
				text = "URL"
			)
		)

		LazyVerticalGrid(
			columns = GridCells.Fixed(3),
			modifier = modifier,
			horizontalArrangement = Arrangement.spacedBy(16.dp)
		) {
			items(coverSourceOptions.size) { index ->
				ActionGridItem(
					icon = coverSourceOptions[index].icon,
					onClick = coverSourceOptions[index].onClick,
					iconDescription = coverSourceOptions[index].iconDescription,
					text = coverSourceOptions[index].text
				)
			}
		}
	}
}

@Composable
private fun ActionGridItem(
	icon: ImageVector,
	onClick: () -> Unit,
	iconDescription: String?,
	text: String,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.clip(RoundedCornerShape(8.dp))
			.border(
				width = 1.dp,
				color = MaterialTheme.colorScheme.outline,
				shape = RoundedCornerShape(8.dp)
			).clickable(onClick = onClick)
			.padding(vertical = 8.dp),
		verticalArrangement = Arrangement.spacedBy(4.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			icon = icon,
			iconDescription = iconDescription,
			iconSize = IconSize.Medium
		)
		Text(text = text)
	}
}

data class IconActionItem(
	val icon: ImageVector,
	val onClick: () -> Unit,
	val iconDescription: String? = null,
	val text: String
)