package com.frost23z.bookshelf.domain.interactor

import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.domain.repository.ContributorsRepository

class GetDetails(
    private val booksRepository: BooksRepository,
    private val contributorsRepository: ContributorsRepository
) {
    suspend fun getBookById(id: Long) = booksRepository.getBookById(id)

    suspend fun deleteBookById(id: Long) = booksRepository.deleteBookById(id)

    suspend fun getContributorsByBookId(bookId: Long) = contributorsRepository.getContributorsByBookId(bookId)
}