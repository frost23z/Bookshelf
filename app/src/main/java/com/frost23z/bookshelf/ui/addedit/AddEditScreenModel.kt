package com.frost23z.bookshelf.ui.addedit

import com.frost23z.bookshelf.ui.addedit.components.AddEditScreenAction
import com.frost23z.bookshelf.ui.addedit.components.AddEditScreenState
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import kotlinx.coroutines.flow.update

class AddEditScreenModel : ScreenModel<AddEditScreenState>(AddEditScreenState()) {
	fun onAction(action: AddEditScreenAction) {
		when (action) {
			is AddEditScreenAction.UpdateBook -> updateState { copy(book = action.updateBook(book), hasUnsavedChanges = true) }
			is AddEditScreenAction.UpdatePublisher -> updateState { copy(publisher = action.publisher, hasUnsavedChanges = true) }
			is AddEditScreenAction.UpdateLanguage -> updateState { copy(language = action.language, hasUnsavedChanges = true) }
			is AddEditScreenAction.UpdateAcquisition -> updateState { copy(acquisition = action.acquisition, hasUnsavedChanges = true) }
			is AddEditScreenAction.UpdateAcquiredFrom -> updateState { copy(acquiredFrom = action.acquiredFrom, hasUnsavedChanges = true) }
			AddEditScreenAction.ToggleDatePickerVisibility -> updateState { copy(isDatePickerVisible = !isDatePickerVisible) }
			AddEditScreenAction.ToggleFormatDialogVisibility -> updateState { copy(isFormatDialogVisible = !isFormatDialogVisible) }
			AddEditScreenAction.ToggleAcquisitionDialogVisibility -> updateState {
				copy(isAcquisitionDialogVisible = !isAcquisitionDialogVisible)
			}
		}
	}

	private fun updateState(update: AddEditScreenState.() -> AddEditScreenState) {
		mutableState.update { it.update() }
	}
}