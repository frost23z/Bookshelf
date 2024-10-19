package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.frost23z.bookshelf.domain.repository.BooksRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImpl(
    private val db : AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): BooksRepository {
    override suspend fun getBookById(id: Long): Books {
        return db.booksQueries.getBookById(id).executeAsOne()
    }

    override fun getBookByIdAsFlow(id: Long): Flow<Books> {
        return db.booksQueries.getBookById(id).asFlow().mapToOne(dispatcher)
    }

    override suspend fun getAllBooks(): List<Books> {
        return db.booksQueries.getAllBooks().executeAsList()
    }

    override fun getAllBooksAsFlow(): Flow<List<Books>> {
        return db.booksQueries.getAllBooks().asFlow().mapToList(dispatcher)
    }
}