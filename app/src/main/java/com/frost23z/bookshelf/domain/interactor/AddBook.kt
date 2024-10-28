package com.frost23z.bookshelf.domain.interactor

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.domain.repository.ContributorsRepository

class AddBook(
    private val booksRepository: BooksRepository,
    private val contributorsRepository: ContributorsRepository
) {
    suspend fun insertBook(book: Books) {
        booksRepository.insertBook(book)
    }

    suspend fun getLastInsertedBookRowId(): Long {
        return booksRepository.getLastInsertedRowId()
    }

    suspend fun getContributorIdByName(contributorName: String): Long? {
        return contributorsRepository.getContributorIdByName(contributorName)
    }

    suspend fun insertContributor(contributor: Contributors) {
        contributorsRepository.insertContributor(contributor)
    }

    suspend fun getLastInsertedContributorRowId(): Long {
        return contributorsRepository.getLastInsertedContributorRowId()
    }

    suspend fun insertBookContributor(map: Books_Contributors_Map) {
        contributorsRepository.insertBookContributor(map)
    }
}