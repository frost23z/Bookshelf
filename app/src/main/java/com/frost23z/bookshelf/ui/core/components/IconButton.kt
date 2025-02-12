package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

/**
 * A customizable IconButton composable that integrates with the custom [Icon] composable.
 *
 * @param icon The [ImageVector] to display as the icon.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param modifier The [Modifier] to apply to the button.
 * @param enabled Controls the enabled state of the button. When `false`, the button will not be clickable.
 * @param iconDescription The content description for accessibility. If null, a default description is generated.
 * @param iconSize The size of the icon.
 * @param iconTint The color tint to apply to the icon.
 * @param tooltip The optional tooltip text to display when the button is hovered or long-pressed.
 * @param buttonSize The size of the button container.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconButton(
	icon: ImageVector,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	iconDescription: String? = null,
	iconSize: Dp = 24.dp,
	iconTint: Color = LocalContentColor.current,
	tooltip: String? = null,
	buttonSize: Dp = 2 * iconSize
) {
	TooltipBox(
		positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
		tooltip = {
			tooltip?.let {
				PlainTooltip { Text(it) }
			}
		},
		state = rememberTooltipState()
	) {
		IconButton(
			onClick = onClick,
			modifier = modifier.size(buttonSize),
			enabled = enabled
		) {
			Icon(
				icon = icon,
				iconDescription = iconDescription,
				iconSize = iconSize,
				iconTint = iconTint,
				modifier = Modifier.size(iconSize)
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreview() {
	var clicked by remember { mutableStateOf(false) }
	IconButton(
		icon = if (clicked) Icons.AutoMirrored.Filled.ArrowBack else Icons.AutoMirrored.Filled.ArrowForward,
		onClick = { clicked = !clicked },
		iconDescription = "Back",
		tooltip = "Navigate back",
		iconSize = 24.dp,
		buttonSize = 48.dp
	)
}