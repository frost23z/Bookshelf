package com.frost23z.bookshelf.data.repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.data.AppDatabase
import com.frost23z.bookshelf.domain.models.LibraryBooks
import com.frost23z.bookshelf.domain.repositories.LibraryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
	private val db: AppDatabase,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LibraryRepository {
	override suspend fun getLibraryAsFlow(): Flow<List<LibraryBooks>> = db.viewQueries
		.getLibrary()
		.asFlow()
		.mapToList(dispatcher)
}