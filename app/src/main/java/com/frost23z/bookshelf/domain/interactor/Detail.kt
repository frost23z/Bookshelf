package com.frost23z.bookshelf.domain.interactor

import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.domain.repository.ContributorsRepository

class Detail(
    private val booksRepository: BooksRepository,
    private val contributorsRepository: ContributorsRepository
) {
    suspend fun getAllBooks() = booksRepository.getAllBooks()
    suspend fun getAllContributors() = contributorsRepository.getAllContributors()
    suspend fun getAllMapping() = contributorsRepository.getAllMapping()
}