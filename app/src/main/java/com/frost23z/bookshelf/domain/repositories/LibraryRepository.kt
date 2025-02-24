package com.frost23z.bookshelf.domain.repositories

import com.frost23z.bookshelf.domain.models.LibraryBooks
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {
	suspend fun getLibraryAsFlow(): Flow<List<LibraryBooks>>
}