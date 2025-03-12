package com.frost23z.bookshelf.ui.addedit.components

import androidx.lifecycle.viewModelScope
import com.frost23z.bookshelf.domain.repositories.AddEditRepository
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import com.frost23z.bookshelf.ui.core.navigation.Destination
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditScreenModel(
	private val repository: AddEditRepository,
	private val navigator: Navigator
) : ScreenModel<AddEditScreenState>(AddEditScreenState()) {
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
			AddEditScreenEvent.SaveBook -> viewModelScope.launch {
				saveBook()
				updateState { copy(hasUnsavedChanges = false) }
				navigator.navigate(Destination.LibraryGraph) {
					popUpTo(Destination.LibraryGraph) { inclusive = true }
					launchSingleTop = true
				}
			}
		}
	}

	private fun updateState(update: AddEditScreenState.() -> AddEditScreenState) {
		mutableState.update { it.update() }
	}

	private suspend fun saveBook() {
		repository.insert(state.value.book, state.value.publisher)
	}
}