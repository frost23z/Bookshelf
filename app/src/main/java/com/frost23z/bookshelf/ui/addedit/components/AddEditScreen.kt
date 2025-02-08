package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Publish
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.addedit.AddEditScreenModel

@Composable
fun AddEditScreen(
	book: Books,
	publisher: String,
	onTitleChange: (String) -> Unit,
	onSubtitleChange: (String) -> Unit,
	onPublisherChange: (String) -> Unit
) {
	val keyboardOptions = KeyboardOptions.Default.copy(autoCorrectEnabled = false, imeAction = ImeAction.Next)

	LazyColumn(
		modifier = Modifier.fillMaxSize(),
		contentPadding = PaddingValues(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		item(key = "TitleSection") {
			TextFieldGroupContainer {
				TextField(
					value = book.title,
					onValueChange = { onTitleChange(it) },
					keyboardOptions = keyboardOptions,
					label = "Title",
					leadingIcon = Icons.Outlined.Title
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
		item(key = "InfoSection") {
			TextFieldGroupContainer {
				TextField(
					value = publisher,
					onValueChange = { onPublisherChange(it) },
					keyboardOptions = keyboardOptions,
					label = "Publisher",
					leadingIcon = Icons.Outlined.Publish
				)
				TextFieldSeparator()
				TextField(
					value = book.publicationDate?.toString() ?: "",
					onValueChange = { },
					keyboardOptions = keyboardOptions,
					label = "Publication Date",
					placeholder = "YYYY-MM-DD",
					trailingIcon = Icons.Outlined.CalendarToday,
					trailingIconClick = {},
					readOnly = true
				)
			}
		}
		item(key = "DataPreview") { Text("Book: $book") }
	}
}

@Preview(showBackground = true)
@Composable
private fun AddEditScreenPreview() {
	val screenModel = AddEditScreenModel()
	val state by screenModel.state.collectAsStateWithLifecycle()
	AddEditScreen(
		book = state.book,
		onTitleChange = { screenModel.updateBook { copy(title = it) } },
		onSubtitleChange = { screenModel.updateBook { copy(subtitle = it) } },
		publisher = state.publisher,
		onPublisherChange = { screenModel.state }
	)
}