package com.frost23z.bookshelf.ui.library.components

import com.frost23z.bookshelf.domain.models.LibraryBooks

data class LibraryScreenState(
	val isLoading: Boolean = true,
	val library: List<LibraryBooks> = emptyList()
)