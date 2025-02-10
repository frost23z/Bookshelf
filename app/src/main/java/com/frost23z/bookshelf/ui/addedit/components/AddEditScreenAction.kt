package com.frost23z.bookshelf.ui.addedit.components

import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.domain.models.Books

sealed class AddEditScreenAction {
	data class UpdateBook(val updateBook: Books.() -> Books) : AddEditScreenAction()

	data class UpdatePublisher(val publisher: String) : AddEditScreenAction()

	data class UpdateLanguage(val language: String) : AddEditScreenAction()

	data class UpdateAcquisition(val acquisition: AcquisitionType) : AddEditScreenAction()

	data class UpdateAcquiredFrom(val acquiredFrom: String) : AddEditScreenAction()

	data object ToggleDatePickerVisibility : AddEditScreenAction()

	data object ToggleFormatDialogVisibility : AddEditScreenAction()

	data object ToggleAcquisitionDialogVisibility : AddEditScreenAction()
}