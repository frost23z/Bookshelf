package com.frost23z.bookshelf.domain.repositories

import com.frost23z.bookshelf.domain.models.Books

interface AddEditRepository {
	suspend fun insert(books: Books)
}