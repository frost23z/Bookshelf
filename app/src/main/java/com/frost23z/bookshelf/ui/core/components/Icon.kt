package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A customizable Icon composable that wraps the default Material Icon.
 *
 * @param icon The [ImageVector] to display as the icon.
 * @param modifier The [Modifier] to apply to the icon.
 * @param iconDescription The content description for accessibility. If null, a default description is generated.
 * @param iconSize The size of the icon.
 * @param containerSize The size of the container (Box) that wraps the icon.
 * @param iconTint The color tint to apply to the icon.
 */
@Composable
fun Icon(
	icon: ImageVector,
	modifier: Modifier = Modifier,
	iconDescription: String? = null,
	iconSize: Dp = 24.dp,
	containerSize: Dp = iconSize,
	iconTint: Color = LocalContentColor.current
) {
	val defaultDescription = remember(icon) {
		iconDescription ?: (getIconName(icon) + " Icon")
	}

	val finalIconSize = if (iconSize > containerSize) containerSize else iconSize

	Box(
		modifier = Modifier.size(containerSize),
		contentAlignment = Alignment.Center
	) {
		Icon(
			imageVector = icon,
			contentDescription = defaultDescription,
			modifier = modifier.size(finalIconSize),
			tint = iconTint
		)
	}
}

/**
 * Extracts and formats the name of the icon from its [ImageVector].
 */
fun getIconName(icon: ImageVector): String = icon.name
	.substringAfterLast('.')
	.replace(Regex("([a-z])([A-Z])"), "$1 $2")
	.replaceFirstChar { it.uppercase() }

@Preview(showBackground = true)
@Composable
fun GetIconNameFunctionPreview(modifier: Modifier = Modifier) {
	Text(text = getIconName(Icons.AutoMirrored.Filled.LibraryBooks))
}