package com.frost23z.bookshelf.domain.interactor

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow

class GetLibraryBooks(
    private val booksRepository: BooksRepository
) {
    suspend fun getAllBooks(): List<Books> {
        return booksRepository.getAllBooks()
    }

    fun getAllBooksAsFlow(): Flow<List<Books>> {
        return booksRepository.getAllBooksAsFlow()
    }
}