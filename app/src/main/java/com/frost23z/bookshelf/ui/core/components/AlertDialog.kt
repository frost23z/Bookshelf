package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.frost23z.bookshelf.ui.core.constants.Padding

@Composable
fun AlertDialog(
	modifier: Modifier = Modifier,
	onDismissRequest: () -> Unit,
	properties: DialogProperties = DialogProperties(),
	icon: ImageVector? = null,
	iconSize: Dp = 24.dp,
	title: String? = null,
	confirmButtonEnabled: Boolean = true,
	confirmButtonText: String = "OK",
	cancelButtonText: String = "Cancel",
	onConfirm: (() -> Unit)? = null,
	onCancel: (() -> Unit)? = if (onConfirm == null) onDismissRequest else null,
	content: @Composable () -> Unit
) {
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = properties,
	) {
		Box(
			modifier = modifier.sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth),
			propagateMinConstraints = true
		) {
			Surface(
				modifier = modifier,
				shape = MaterialTheme.shapes.medium,
				tonalElevation = 4.dp,
			) {
				Column(modifier = Modifier.padding(DialogPadding)) {
					icon?.let {
						Box(
							Modifier.padding(IconPadding).align(Alignment.CenterHorizontally)
						) {
							Icon(icon = icon, iconSize = iconSize)
						}
					}
					title?.let {
						Box(
							// Align the title to the center when an icon is present.
							Modifier
								.padding(TitlePadding)
								.align(
									if (icon == null) {
										Alignment.Start
									} else {
										Alignment.CenterHorizontally
									}
								)
						) {
							Text(text = title, style = MaterialTheme.typography.headlineSmall)
						}
					}
					content()
					Row(
						modifier = Modifier.fillMaxWidth().padding(ContentPadding),
						horizontalArrangement = Arrangement.spacedBy(Padding.Small, Alignment.End),
					) {
						if (onCancel != null && onConfirm != null) {
							TextButton(onClick = onCancel) { Text(cancelButtonText) }
						}
						if (onConfirm != null) {
							TextButton(onClick = onConfirm, enabled = confirmButtonEnabled) { Text(confirmButtonText) }
						}
					}
				}
			}
		}
	}
}

val DialogMinWidth = 280.dp
internal val DialogMaxWidth = 560.dp

// Paddings for each of the dialog's parts.
private val DialogPadding = PaddingValues(all = 24.dp)
private val IconPadding = PaddingValues(bottom = 16.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val ContentPadding = PaddingValues(top = 8.dp)

@Preview
@Composable
private fun AlertDialogPreview() {
	AlertDialog(
		icon = Icons.Default.AddAPhoto,
		title = "Title",
		content = {
			Text("Content")
		},
		onDismissRequest = {},
		onConfirm = {},
		onCancel = {},
		properties = DialogProperties()
	)
}