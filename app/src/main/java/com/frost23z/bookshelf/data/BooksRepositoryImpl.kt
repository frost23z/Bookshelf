package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.frost23z.bookshelf.domain.BooksRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class BooksRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BooksRepository {
    override suspend fun getBookById(id: Long): Books = db.booksQueries.getBookById(id).executeAsOne()

    override fun getBookByIdAsFlow(id: Long): Flow<Books> = db.booksQueries
        .getBookById(id)
        .asFlow()
        .mapToOne(dispatcher)

    override suspend fun getAllBooks(): List<Books> = db.booksQueries.getAllBooks().executeAsList()

    override fun getAllBooksAsFlow(): Flow<List<Books>> = db.booksQueries
        .getAllBooks()
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun getLastInsertedRowId(): Long = db.booksQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertBook(book: Books) = db.booksQueries.insertBook(
        favorite = book.favorite,
        dateAdded = Clock.System.now().toEpochMilliseconds(),
        dateLastUpdated = Clock.System.now().toEpochMilliseconds(),
        title = book.title,
        titlePrefix = book.titlePrefix,
        titleSuffix = book.titleSuffix,
        coverUri = book.coverUri,
        description = book.description,
        publisher = book.publisher,
        language = book.language,
        totalPages = book.totalPages,
        format = book.format,
        purchaseFrom = book.purchaseFrom,
        purchasePrice = book.purchasePrice,
        purchaseDate = book.purchaseDate,
        readStatus = book.readStatus,
        readPages = book.readPages,
        startReadingDate = book.startReadingDate,
        finishedReadingDate = book.finishedReadingDate,
        series = book.series,
        volume = book.volume,
        isLent = book.isLent,
        lentTo = book.lentTo,
        lentDate = book.lentDate,
        lentReturned = book.lentReturned
    )

    override suspend fun updateBook(
        id: Long,
        book: Books
    ) = db.booksQueries.updateBook(
        id = id,
        favorite = book.favorite,
        dateAdded = book.dateAdded,
        dateLastUpdated = Clock.System.now().toEpochMilliseconds(),
        title = book.title,
        titlePrefix = book.titlePrefix,
        titleSuffix = book.titleSuffix,
        coverUri = book.coverUri,
        description = book.description,
        publisher = book.publisher,
        language = book.language,
        totalPages = book.totalPages,
        format = book.format,
        purchaseFrom = book.purchaseFrom,
        purchasePrice = book.purchasePrice,
        purchaseDate = book.purchaseDate,
        readStatus = book.readStatus,
        readPages = book.readPages,
        startReadingDate = book.startReadingDate,
        finishedReadingDate = book.finishedReadingDate,
        series = book.series,
        volume = book.volume,
        isLent = book.isLent,
        lentTo = book.lentTo,
        lentDate = book.lentDate,
        lentReturned = book.lentReturned
    )

    override suspend fun deleteBook(id: Long) = db.booksQueries.deleteBook(id)
}