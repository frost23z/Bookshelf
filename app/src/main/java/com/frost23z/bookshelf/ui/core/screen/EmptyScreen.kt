package com.frost23z.bookshelf.ui.core.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.constants.IconSize
import com.frost23z.bookshelf.ui.core.constants.Padding

@Composable
fun EmptyScreen(
	message: String,
	modifier: Modifier = Modifier,
	icon: ImageVector? = null,
	subtitle: String? = null
) {
	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		icon?.let {
			Icon(
				icon = it,
				iconSize = IconSize.XXLarge,
				iconTint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
			)
			Spacer(modifier = Modifier.height(Padding.Medium))
		}
		Text(
			text = message,
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
			textAlign = TextAlign.Center
		)
		subtitle?.let {
			Spacer(modifier = Modifier.height(Padding.XSmall))
			Text(
				text = it,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
				textAlign = TextAlign.Center
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
private fun EmptyScreenPreview() {
	EmptyScreen(
		message = "No books found",
		icon = Icons.AutoMirrored.Filled.LibraryBooks,
		subtitle = "Try to add a new book"
	)
}