package com.frost23z.bookshelf.ui.addedit

import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import com.frost23z.bookshelf.ui.core.models.UIState
import kotlinx.coroutines.flow.update

class AddEditScreenModel : ScreenModel<UIState.AddEdit>(UIState.AddEdit()) {
	fun updateBook(update: Books.() -> Books) {
		mutableState.update {
			it.copy(book = it.book.update(), hasUnsavedChanges = true)
		}
	}
}