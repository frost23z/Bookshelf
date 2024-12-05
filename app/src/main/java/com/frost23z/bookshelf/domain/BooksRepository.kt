package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Books
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getBookById(id: Long): Books

    fun getBookByIdAsFlow(id: Long): Flow<Books>

    suspend fun getAllBooks(): List<Books>

    fun getAllBooksAsFlow(): Flow<List<Books>>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertBook(book: Books)

    suspend fun updateBook(
        id: Long,
        book: Books
    )

    suspend fun deleteBook(id: Long)
}