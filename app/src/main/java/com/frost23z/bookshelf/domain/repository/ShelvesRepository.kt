package com.frost23z.bookshelf.domain.repository

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Shelves
import kotlinx.coroutines.flow.Flow

interface ShelvesRepository {
    suspend fun getShelfById(id: Long): Shelves

    suspend fun getShelfByName(name: String): Long?

    suspend fun getAllShelves(): List<Shelves>

    fun getAllShelvesAsFlow(): Flow<List<Shelves>>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertShelf(shelf: Shelves)

    suspend fun updateShelf(shelf: Shelves)

    suspend fun deleteShelf(id: Long)

    // Mapping operations
    suspend fun getShelvesByBook(bookId: Long): List<Shelves>

    suspend fun getBooksByShelf(shelfId: Long): List<Books>

    suspend fun insertBookShelf(
        bookId: Long,
        shelfId: Long
    )

    suspend fun deleteBookShelfByBook(bookId: Long)

    suspend fun deleteBookShelfByShelf(shelfId: Long)

    suspend fun deleteBookShelf(
        bookId: Long,
        shelfId: Long
    )
}