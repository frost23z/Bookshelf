package com.frost23z.bookshelf.ui.addedit.components

import com.frost23z.bookshelf.ui.core.components.ScreenModel
import kotlinx.coroutines.flow.update

class AddEditScreenModel : ScreenModel<AddEditScreenState>(AddEditScreenState()) {
	fun onEvent(event: AddEditScreenEvent) {
		when (event) {
			is AddEditScreenEvent.UpdateBook -> updateState { copy(book = event.updateBook(book), hasUnsavedChanges = true) }
			is AddEditScreenEvent.UpdateCoverSelectionState -> updateState { copy(coverSelectionState = event.coverSelectionState) }
			is AddEditScreenEvent.UpdatePublisher -> updateState { copy(publisher = event.publisher, hasUnsavedChanges = true) }
			is AddEditScreenEvent.UpdateLanguage -> updateState { copy(language = event.language, hasUnsavedChanges = true) }
			is AddEditScreenEvent.UpdateAcquisition -> updateState { copy(acquisition = event.acquisition, hasUnsavedChanges = true) }
			is AddEditScreenEvent.UpdateAcquiredFrom -> updateState { copy(acquiredFrom = event.acquiredFrom, hasUnsavedChanges = true) }
			is AddEditScreenEvent.ShowDatePickerFor -> updateState { copy(datePickerFor = event.datePickerFor) }
			is AddEditScreenEvent.ShowDialogFor -> updateState { copy(dialogFor = event.dialogFor) }
		}
	}

	private fun updateState(update: AddEditScreenState.() -> AddEditScreenState) {
		mutableState.update { it.update() }
	}
}