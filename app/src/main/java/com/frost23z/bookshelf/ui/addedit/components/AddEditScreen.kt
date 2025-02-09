package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import com.frost23z.bookshelf.ui.core.models.UIState
import com.frost23z.bookshelf.ui.theme.BookshelfTheme
import kotlinx.datetime.LocalDate

@Composable
fun AddEditScreen(
	state: UIState.AddEdit,
	onTitleChange: (String) -> Unit,
	onSubtitleChange: (String) -> Unit,
	onPublisherChange: (String) -> Unit,
	onPublicationDateChange: (LocalDate?) -> Unit,
	onLanguageChange: (String) -> Unit,
	onTotalPagesChange: (Long?) -> Unit,
	toggleDatePickerVisibility: () -> Unit,
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
					onValueChange = { onTitleChange(it) },
					keyboardOptions = keyboardOptions,
					label = "Title",
					leadingIcon = Icons.Outlined.Title
				)
				TextFieldSeparator()
				TextField(
					value = state.book.subtitle ?: "",
					onValueChange = { onSubtitleChange(it) },
					keyboardOptions = keyboardOptions,
					label = "Subtitle"
				)
			}
		}
		item(key = "InfoSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.publisher,
					onValueChange = { onPublisherChange(it) },
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
					trailingIconClick = { toggleDatePickerVisibility() },
					readOnly = true
				)
				TextFieldSeparator()
				TextField(
					value = state.language,
					onValueChange = { onLanguageChange(it) },
					keyboardOptions = keyboardOptions,
					label = "Language",
					leadingIcon = Icons.Outlined.Language
				)
				TextFieldSeparator()
				TextField(
					value = state.book.totalPages?.toString() ?: "",
					onValueChange = { if (it.length < 5 && it.isDigitsOnly()) onTotalPagesChange(it.toLongOrNull()) },
					keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
					label = "Total Pages",
					leadingIcon = Icons.Outlined.AutoStories
				)
			}
		}
		item(key = "DataPreview") { Text("Book: $state") }
	}

	if (state.isDatePickerVisible) {
		DatePickerModal(
			onDateSelected = { onPublicationDateChange(it) },
			onDismiss = {
				toggleDatePickerVisibility()
			}
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
				onTitleChange = { screenModel.updateBook { copy(title = it) } },
				onSubtitleChange = { screenModel.updateBook { copy(subtitle = it) } },
				onPublisherChange = { screenModel.updatePublisher(it) },
				onPublicationDateChange = { screenModel.updateBook { copy(publicationDate = it) } },
				onLanguageChange = { screenModel.updateLanguage(it) },
				onTotalPagesChange = { screenModel.updateBook { copy(totalPages = it) } },
				toggleDatePickerVisibility = { screenModel.toggleDatePickerVisibility() }
			)
		}
	}
}