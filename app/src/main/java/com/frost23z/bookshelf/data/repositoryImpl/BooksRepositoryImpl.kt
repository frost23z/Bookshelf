package com.frost23z.bookshelf.data.repositoryImpl

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.frost23z.bookshelf.data.AppDatabase
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.domain.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImpl(
    private val db: AppDatabase
) : BooksRepository {

    private val queries = db.booksQueries

    override suspend fun getBooksById(id: Long): Books {
        return queries.getBookById(id).executeAsOne()
    }

    override suspend fun getBooksByIdAsFlow(id: Long): Flow<Books> {
        return queries.getBookById(id).asFlow().mapToOne(Dispatchers.IO)
    }

    override suspend fun getAllBooks(): List<Books> {
        return queries.getAllBooks().executeAsList()
    }

    override fun getAllBooksAsFlow(): Flow<List<Books>> {
        return queries.getAllBooks().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun insertBook(book: Books) {
        queries.insertBook(
            titlePrefix = book.titlePrefix,
            title = book.title,
            titleSuffix = book.titleSuffix,
            coverUrl = book.coverUrl,
            description = book.description,
            favorite = book.favorite,
            publisher = book.publisher,
            format = book.format,
            language = book.language,
            dateAdded = book.dateAdded,
            series = book.series,
            volume = book.volume,
            acquiredDate = book.acquiredDate,
            acquiredSource = book.acquiredSource,
            price = book.price,
            totalPages = book.totalPages,
            status = book.status,
            readPages = book.readPages
        )
    }
}