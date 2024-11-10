package com.frost23z.bookshelf.domain.repository

import com.frost23z.bookshelf.data.Books
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    suspend fun getBookById(id: Long): Books

    fun getBookByIdAsFlow(id: Long): Flow<Books>

    suspend fun getAllBooks(): List<Books>

    fun getAllBooksAsFlow(): Flow<List<Books>>

    suspend fun getFavoriteBooks(): List<Books>

    fun getFavoriteBooksAsFlow(): Flow<List<Books>>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertBook(book: Books)

    suspend fun updateBookById(
        id: Long,
        book: Books
    )

    suspend fun deleteBookById(id: Long)
}