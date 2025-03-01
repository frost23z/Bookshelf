package com.frost23z.bookshelf.ui.library.components

import androidx.lifecycle.viewModelScope
import com.frost23z.bookshelf.domain.repositories.LibraryRepository
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryScreenModel(
	private val repository: LibraryRepository
) : ScreenModel<LibraryScreenState>(LibraryScreenState()) {
	fun onEvent(event: LibraryScreenEvent) {
	}

	private fun updateState(update: LibraryScreenState.() -> LibraryScreenState) {
		mutableState.update { it.update() }
	}

	init {
		viewModelScope.launch {
			repository.getLibraryAsFlow().collect { library ->
				updateState { copy(library = library, isLoading = false) }
			}
		}
	}
}