package com.frost23z.bookshelf.domain.interactor

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.domain.repository.BooksRepository

class AddBook(
    private val booksRepository: BooksRepository
) {
    suspend fun insertBook(book: Books) {
        booksRepository.insertBook(book)
    }
}