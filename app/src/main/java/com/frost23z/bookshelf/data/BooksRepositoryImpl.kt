package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.frost23z.bookshelf.domain.repository.BooksRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BooksRepository {
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

    override suspend fun insertBook(book: Books) {
        db.booksQueries.insert(
            favorite = book.favorite,
            dateAdded = book.dateAdded,
            title = book.title,
            titlePrefix = book.titlePrefix,
            titleSuffix = book.titleSuffix,
            coverUrl = book.coverUrl,
            summary = book.summary,
            publisher = book.publisher,
            language = book.language,
            pages = book.pages,
            format = book.format,
            purchaseFrom = book.purchaseFrom,
            purchasePrice = book.purchasePrice,
            purchaseDate = book.purchaseDate,
            status = book.status,
            readPages = book.readPages,
            series = book.series,
            volume = book.volume
        )
    }
}