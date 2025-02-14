package com.frost23z.bookshelf.ui.library.components

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.ListItem
import com.frost23z.bookshelf.ui.core.constants.IconSize

@Composable
fun LibraryListViewItem(
	headlineContent: String,
	modifier: Modifier = Modifier,
	overlineContent: String? = null,
	supportingContent: String? = null,
	leadingContent: Uri? = null,
	leadingContentSize: Dp = IconSize.Large,
	trailingContent: @Composable (() -> Unit)? = null,
	colors: ListItemColors = ListItemDefaults.colors(),
	tonalElevation: Dp = ListItemDefaults.Elevation,
	shadowElevation: Dp = ListItemDefaults.Elevation
) {
	ListItem(
		headlineContent = headlineContent,
		modifier = modifier,
		overlineContent = overlineContent,
		supportingContent = supportingContent,
		leadingContent = {
			// TODO: This uses Subcomposition, which should be avoided in high-performance scenarios like LazyRow/LazyColumn.  If performance degrades, consider optimizing or finding an alternative approach.
			SubcomposeAsyncImage(
				model = ImageRequest
					.Builder(LocalContext.current)
					.data(leadingContent)
					.crossfade(true)
					.build(),
				contentDescription = "Book Cover",
				modifier = Modifier.size(leadingContentSize).clip(RoundedCornerShape(4.dp)),
				loading = { CircularProgressIndicator(modifier = Modifier.size(leadingContentSize / 2)) },
				error = { Icon(icon = Icons.Outlined.AutoStories, iconSize = leadingContentSize) },
				contentScale = ContentScale.Crop,
			)
		},
		trailingContent = trailingContent,
		colors = colors,
		tonalElevation = tonalElevation,
		shadowElevation = shadowElevation
	)
}

@Preview
@Composable
private fun LibraryListViewItemPreview() {
	LazyColumn {
		items(10) {
			LibraryListViewItem(
				headlineContent = "Headline",
				overlineContent = "Overline",
				supportingContent = "Supporting",
				leadingContent = Uri.parse("https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg")
			)
		}
	}
}