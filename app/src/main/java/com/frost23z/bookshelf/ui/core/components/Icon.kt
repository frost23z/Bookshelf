package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Icon(
	icon: ImageVector,
	modifier: Modifier = Modifier,
	iconDescription: String? = null,
	iconSize: Dp = 24.dp,
	containerSize: Dp = iconSize,
	iconTint: Color = LocalContentColor.current
) {
	Box(
		modifier = Modifier.size(containerSize),
		contentAlignment = Alignment.Center
	) {
		Column {
			Icon(
				imageVector = icon,
				contentDescription = iconDescription ?: (getIconName(icon) + " Icon"),
				modifier = modifier.size(iconSize),
				tint = iconTint
			)
		}
	}
}

fun getIconName(icon: ImageVector): String = icon.name
	.substringAfterLast('.')
	.replace(Regex("([a-z])([A-Z])"), "$1 $2")
	.replaceFirstChar { it.uppercase() }

@Preview(showBackground = true)
@Composable
fun GetIconNameFunctionPreview(modifier: Modifier = Modifier) {
	Text(text = getIconName(Icons.AutoMirrored.Filled.LibraryBooks))
}