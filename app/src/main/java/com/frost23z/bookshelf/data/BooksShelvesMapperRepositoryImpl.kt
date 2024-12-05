package com.frost23z.bookshelf.data

import com.frost23z.bookshelf.domain.BooksShelvesMapperRepository

class BooksShelvesMapperRepositoryImpl(
    private val db: AppDatabase
) : BooksShelvesMapperRepository {
    override suspend fun getShelvesByBookId(bookId: Long): List<Shelves> =
        db.booksShelvesMapperQueries.getShelvesByBookId(bookId).executeAsList()

    override suspend fun getBooksByShelfId(shelfId: Long): List<Books> =
        db.booksShelvesMapperQueries.getBooksByShelfId(shelfId).executeAsList()

    override suspend fun getAllBookShelves(): List<BooksShelvesMapper> = db.booksShelvesMapperQueries.getAllBookShelves().executeAsList()

    override suspend fun getLastInsertedRowId(): Long = db.booksShelvesMapperQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertBookShelf(
        bookId: Long,
        shelfId: Long
    ) = db.booksShelvesMapperQueries.insertBookShelf(bookId, shelfId)

    override suspend fun deleteBookShelvesByBook(bookId: Long) = db.booksShelvesMapperQueries.deleteBookShelvesByBook(bookId)

    override suspend fun deleteBookShelvesByShelf(shelfId: Long) = db.booksShelvesMapperQueries.deleteBookShelvesByShelf(shelfId)

    override suspend fun deleteBookShelfMapping(
        bookId: Long,
        shelfId: Long
    ) = db.booksShelvesMapperQueries.deleteBookShelfMapping(bookId, shelfId)
}