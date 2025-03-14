package com.frost23z.bookshelf.ui.library.components

sealed class LibraryScreenEvent {
	data class UpdateQuery(val query: String?) : LibraryScreenEvent()

	data class OpenDetail(val bookID: Long) : LibraryScreenEvent()
}