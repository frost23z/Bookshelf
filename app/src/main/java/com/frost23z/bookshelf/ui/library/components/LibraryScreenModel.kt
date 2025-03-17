package com.frost23z.bookshelf.ui.library.components

import androidx.lifecycle.viewModelScope
import com.frost23z.bookshelf.domain.repositories.LibraryRepository
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import com.frost23z.bookshelf.ui.core.navigation.Destination
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryScreenModel(
	private val repository: LibraryRepository,
	private val navigator: Navigator
) : ScreenModel<LibraryScreenState>(LibraryScreenState()) {
	fun onEvent(event: LibraryScreenEvent) {
		when (event) {
			is LibraryScreenEvent.UpdateQuery -> updateState { copy(query = event.query) }
			is LibraryScreenEvent.OpenDetail -> {
				viewModelScope.launch {
					navigator.navigate(Destination.Detail(event.bookID))
				}
			}
		}
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