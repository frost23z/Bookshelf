package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Note
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Publish
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frost23z.bookshelf.ui.addedit.AddEditScreenModel
import com.frost23z.bookshelf.ui.core.components.DatePickerModal
import com.frost23z.bookshelf.ui.core.components.SingleChoiceDialog
import com.frost23z.bookshelf.ui.core.models.toDisplayString
import com.frost23z.bookshelf.ui.theme.BookshelfTheme

@Composable
fun AddEditScreen(
	state: AddEditScreenState,
	onAction: (AddEditScreenAction) -> Unit,
	modifier: Modifier = Modifier
) {
	val keyboardOptions = KeyboardOptions.Default.copy(autoCorrectEnabled = false, imeAction = ImeAction.Next)

	LazyColumn(
		modifier = modifier.fillMaxSize(),
		contentPadding = PaddingValues(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		item(key = "TitleSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.book.title,
					onValueChange = { onAction(AddEditScreenAction.UpdateBook { copy(title = it) }) },
					keyboardOptions = keyboardOptions,
					label = "Title",
					leadingIcon = Icons.Outlined.Title
				)
				TextFieldSeparator()
				TextField(
					value = state.book.subtitle ?: "",
					onValueChange = { onAction(AddEditScreenAction.UpdateBook { copy(subtitle = it) }) },
					keyboardOptions = keyboardOptions,
					label = "Subtitle"
				)
			}
		}
		item(key = "InfoSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.publisher,
					onValueChange = { onAction(AddEditScreenAction.UpdatePublisher(it)) },
					keyboardOptions = keyboardOptions,
					label = "Publisher",
					leadingIcon = Icons.Outlined.Publish
				)
				TextFieldSeparator()
				TextField(
					value = state.book.publicationDate?.toString() ?: "",
					onValueChange = { },
					keyboardOptions = keyboardOptions,
					label = "Publication Date",
					placeholder = "YYYY-MM-DD",
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.isDatePickerVisible) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleDatePickerVisibility) },
					readOnly = true
				)
				TextFieldSeparator()
				TextField(
					value = state.language,
					onValueChange = { onAction(AddEditScreenAction.UpdateLanguage(it)) },
					keyboardOptions = keyboardOptions,
					label = "Language",
					leadingIcon = Icons.Outlined.Language
				)
				TextFieldSeparator()
				TextField(
					value = state.book.totalPages?.toString() ?: "",
					onValueChange = {
						if (it.length < 5 && it.isDigitsOnly()) {
							onAction(AddEditScreenAction.UpdateBook { copy(totalPages = it.toLongOrNull()) })
						}
					},
					keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
					label = "Total Pages",
					leadingIcon = Icons.Outlined.AutoStories
				)
				TextFieldSeparator()
				TextField(
					value = state.book.format?.toDisplayString() ?: "",
					onValueChange = { },
					keyboardOptions = keyboardOptions,
					label = "Format",
					leadingIcon = Icons.AutoMirrored.Outlined.Note,
					trailingIcon = if (state.isDatePickerVisible) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleFormatDialogVisibility) },
					readOnly = true
				)
			}
		}
		item(key = "DataPreview") { Text("Book: $state") }
	}

	if (state.isDatePickerVisible) {
		DatePickerModal(
			onDateSelected = { onAction(AddEditScreenAction.UpdateBook { copy(publicationDate = it) }) },
			onDismiss = { onAction(AddEditScreenAction.ToggleDatePickerVisibility) }
		)
	}
	if (state.isFormatDialogVisible) {
		SingleChoiceDialog(
			selectedOption = state.book.format,
			displayString = { it.toDisplayString() },
			onOptionSelected = { onAction(AddEditScreenAction.UpdateBook { copy(format = it) }) },
			onDismissRequest = { onAction(AddEditScreenAction.ToggleFormatDialogVisibility) },
		)
	}
}

@PreviewLightDark
@Composable
private fun AddEditScreenPreview() {
	val screenModel by remember { mutableStateOf(AddEditScreenModel()) }
	val state by screenModel.state.collectAsStateWithLifecycle()
	BookshelfTheme {
		Surface {
			AddEditScreen(
				state = state,
				onAction = screenModel::onAction
			)
		}
	}
}