package com.frost23z.bookshelf.ui.addedit.components

import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.addedit.models.CoverSelectionState
import com.frost23z.bookshelf.ui.addedit.models.DatePickerFor

sealed class AddEditScreenEvent {
	data class UpdateBook(val updateBook: Books.() -> Books) : AddEditScreenEvent()

	data class UpdateCoverSelectionState(val coverSelectionState: CoverSelectionState) : AddEditScreenEvent()

	data class UpdatePublisher(val publisher: String) : AddEditScreenEvent()

	data class UpdateLanguage(val language: String) : AddEditScreenEvent()

	data class UpdateAcquisition(val acquisition: AcquisitionType) : AddEditScreenEvent()

	data class UpdateAcquiredFrom(val acquiredFrom: String) : AddEditScreenEvent()

	data class ToggleDatePickerVisibility(val datePickerFor: DatePickerFor?) : AddEditScreenEvent()

	data object ToggleFormatDialogVisibility : AddEditScreenEvent()

	data object ToggleAcquisitionDialogVisibility : AddEditScreenEvent()

	data object ToggleReadStatusDialogVisibility : AddEditScreenEvent()
}