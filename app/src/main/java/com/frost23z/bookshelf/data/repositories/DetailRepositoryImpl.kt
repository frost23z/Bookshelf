package com.frost23z.bookshelf.data.repositories

import com.frost23z.bookshelf.data.AppDatabase
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.domain.repositories.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher

class DetailRepositoryImpl(
	private val db: AppDatabase,
	private val dispatcher: CoroutineDispatcher
) : DetailRepository {
	override suspend fun getBook(bookID: Long): Books = db.selectByIdQueries.getBookById(bookID).executeAsOne()
}