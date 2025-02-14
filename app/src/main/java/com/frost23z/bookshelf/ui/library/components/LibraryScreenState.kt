package com.frost23z.bookshelf.ui.library.components

import androidx.compose.runtime.Immutable
import com.frost23z.bookshelf.domain.models.LibraryBooks

@Immutable
data class LibraryScreenState(
	val isLoading: Boolean = true,
	val library: List<LibraryBooks> = emptyList()
)