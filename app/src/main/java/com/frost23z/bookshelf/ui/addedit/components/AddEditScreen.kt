package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.core.models.booksInit

@Composable
fun AddEditScreen(
	book: Books,
	onTitleChange: (String) -> Unit,
	onSubtitleChange: (String) -> Unit
) {
	val keyboardOptions = KeyboardOptions.Default.copy(autoCorrectEnabled = false, imeAction = ImeAction.Next)

	// TitleSection
	TextFieldGroupContainer {
		TextField(
			value = book.title,
			onValueChange = { onTitleChange(it) },
			keyboardOptions = keyboardOptions,
			label = "Title",
			leadingIcon = Icons.Outlined.Title,
		)
		TextFieldSeparator()
		TextField(
			value = book.subtitle ?: "",
			onValueChange = { onSubtitleChange(it) },
			keyboardOptions = keyboardOptions,
			label = "Subtitle"
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun AddEditScreenPreview() {
	var book by remember { mutableStateOf(booksInit()) }
	LazyColumn(
		contentPadding = PaddingValues(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		item(key = "AddEditScreen") {
			AddEditScreen(
				book = book,
				onTitleChange = { book = book.copy(title = it) },
				onSubtitleChange = { book = book.copy(subtitle = it) }
			)
		}
		item {
			Text("Book: $book")
		}
	}
}