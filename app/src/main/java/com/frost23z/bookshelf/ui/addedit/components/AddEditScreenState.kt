package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.runtime.Immutable
import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.addedit.models.CoverSelectionState
import com.frost23z.bookshelf.ui.addedit.models.DatePickerFor
import com.frost23z.bookshelf.ui.core.models.booksInit

@Immutable
data class AddEditScreenState(
	val book: Books = booksInit(),
	val coverSelectionState: CoverSelectionState = CoverSelectionState.NONE,
	val publisher: String = "",
	val publisherSuggestions: List<String> = emptyList(),
	val language: String = "",
	val acquisition: AcquisitionType? = null,
	val acquiredFrom: String = "",
	val datePickerFor: DatePickerFor? = null,
	val dialogFor: DialogFor? = null,
	val hasUnsavedChanges: Boolean = false
)