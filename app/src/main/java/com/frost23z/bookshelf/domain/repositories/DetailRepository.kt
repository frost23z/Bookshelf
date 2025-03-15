package com.frost23z.bookshelf.domain.repositories

import com.frost23z.bookshelf.domain.models.Books

interface DetailRepository {
	suspend fun getBook(bookID: Long): Books
}