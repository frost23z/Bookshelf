package com.frost23z.bookshelf.ui.core.models

import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.domain.models.LibraryBooks

sealed class UIState {
	data class Library(
		val books: List<LibraryBooks> = emptyList(),
		val isLoading: Boolean = false
	)

	data class AddEdit(
		val book: Books = booksInit(),
		val hasUnsavedChanges: Boolean = false,
		val publisher: String = "",
		val language: String = "",
		val isDatePickerVisible: Boolean = false,
	)
}