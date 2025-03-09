package com.frost23z.bookshelf.ui.detail.components

import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.ui.core.models.booksInit

data class DetailScreenState(
	val bookID: Long = 0,
	val books: Books = booksInit()
)
