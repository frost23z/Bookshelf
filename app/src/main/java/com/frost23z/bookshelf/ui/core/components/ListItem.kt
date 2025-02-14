package com.frost23z.bookshelf.ui.core.components

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ListItem(
	headlineContent: String,
	modifier: Modifier = Modifier,
	overlineContent: String? = null,
	supportingContent: String? = null,
	leadingContent: @Composable (() -> Unit)? = null,
	trailingContent: @Composable (() -> Unit)? = null,
	colors: ListItemColors = ListItemDefaults.colors(),
	tonalElevation: Dp = ListItemDefaults.Elevation,
	shadowElevation: Dp = ListItemDefaults.Elevation
) {
	ListItem(
		headlineContent = { Text(headlineContent) },
		modifier = modifier,
		overlineContent = overlineContent?.let { { Text(it) } },
		supportingContent = supportingContent?.let { { Text(it) } },
		leadingContent = leadingContent,
		trailingContent = trailingContent,
		colors = colors,
		tonalElevation = tonalElevation,
		shadowElevation = shadowElevation
	)
}