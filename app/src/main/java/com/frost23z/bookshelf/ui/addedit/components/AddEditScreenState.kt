package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.runtime.Immutable
import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.addedit.models.DatePickerFor
import com.frost23z.bookshelf.ui.core.models.booksInit

@Immutable
data class AddEditScreenState(
	val book: Books = booksInit(),
	val hasUnsavedChanges: Boolean = false,
	val publisher: String = "",
	val language: String = "",
	val acquisition: AcquisitionType? = null,
	val acquiredFrom: String = "",
	val datePickerFor: DatePickerFor? = null,
	val isFormatDialogVisible: Boolean = false,
	val isAcquisitionDialogVisible: Boolean = false,
	val isReadStatusDialogVisible: Boolean = false,
)