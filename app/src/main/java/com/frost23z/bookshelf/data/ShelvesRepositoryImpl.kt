package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.domain.repository.ShelvesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ShelvesRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShelvesRepository {
    override suspend fun getShelfById(id: Long): Shelves {
        return db.shelvesQueries.getShelfById(id).executeAsOne()
    }

    override suspend fun getShelfByName(name: String): Long? {
        return db.shelvesQueries.getShelfByName(name.trim()).executeAsOneOrNull()
    }

    override suspend fun getAllShelves(): List<Shelves> {
        return db.shelvesQueries.getAllShelves().executeAsList()
    }

    override fun getAllShelvesAsFlow(): Flow<List<Shelves>> {
        return db.shelvesQueries
            .getAllShelves()
            .asFlow()
            .mapToList(dispatcher)
    }

    override suspend fun getLastInsertedRowId(): Long {
        return db.shelvesQueries.getLastInsertedRowId().executeAsOne()
    }

    override suspend fun insertShelf(shelf: Shelves) {
        db.shelvesQueries.insertShelf(name = shelf.name)
    }

    override suspend fun updateShelfById(shelf: Shelves) {
        db.shelvesQueries.updateShelfById(name = shelf.name, id = shelf.id)
    }

    override suspend fun deleteShelfById(id: Long) {
        db.shelvesQueries.deleteShelfById(id)
    }

    override suspend fun getShelvesByBookId(bookId: Long): List<Shelves> {
        return db.books_Shelves_MapQueries.getShelvesByBookId(bookId).executeAsList()
    }

    override suspend fun getBooksByShelfId(shelfId: Long): List<Books> {
        return db.books_Shelves_MapQueries.getBooksByShelfId(shelfId).executeAsList()
    }

    override suspend fun insertBookShelf(
        bookId: Long,
        shelfId: Long
    ) {
        db.books_Shelves_MapQueries.insertBookShelf(bookId, shelfId)
    }

    override suspend fun deleteBookShelfByBookId(bookId: Long) {
        db.books_Shelves_MapQueries.deleteBookShelfByBookId(bookId)
    }

    override suspend fun deleteBookShelfByShelfId(shelfId: Long) {
        db.books_Shelves_MapQueries.deleteBookShelfByShelfId(shelfId)
    }

    override suspend fun deleteBookShelf(
        bookId: Long,
        shelfId: Long
    ) {
        db.books_Shelves_MapQueries.deleteBookShelf(bookId, shelfId)
    }

    override suspend fun updateShelf(shelf: Shelves) {
        db.shelvesQueries.updateShelf(name = shelf.name, id = shelf.id)
    }

    override suspend fun deleteShelf(id: Long) {
        db.shelvesQueries.deleteShelf(id)
    }

    override suspend fun getShelvesByBook(bookId: Long): List<Shelves> {
        return db.books_Shelves_MapQueries.getShelvesByBookId(bookId).executeAsList()
    }

    override suspend fun getBooksByShelf(shelfId: Long): List<Books> {
        return db.books_Shelves_MapQueries.getBooksByShelfId(shelfId).executeAsList()
    }
}