package com.frost23z.bookshelf.ui.library.components

import com.frost23z.bookshelf.ui.core.components.ScreenModel
import kotlinx.coroutines.flow.update

class LibraryScreenModel : ScreenModel<LibraryScreenState>(LibraryScreenState()) {
	fun onAction(action: LibraryScreenAction) {
	}

	private fun updateState(update: LibraryScreenState.() -> LibraryScreenState) {
		mutableState.update { it.update() }
	}
}