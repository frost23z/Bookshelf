package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.runtime.Immutable
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.core.models.booksInit

@Immutable
data class AddEditScreenState(
	val book: Books = booksInit(),
	val hasUnsavedChanges: Boolean = false,
	val publisher: String = "",
	val language: String = "",
	val isDatePickerVisible: Boolean = false,
	val isFormatDialogVisible: Boolean = false
)