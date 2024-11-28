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
    suspend fun getBookById(id: Long): Books {
        return booksRepository.getBookById(id)
    }

    suspend fun insertBook(book: Books) {
        booksRepository.insertBook(book)
    }

    suspend fun getLastInsertedBookId(): Long {
        return booksRepository.getLastInsertedRowId()
    }

    suspend fun getContributorByName(contributorName: String): Long? {
        return contributorsRepository.getContributorByName(contributorName)
    }

    suspend fun insertContributor(contributor: Contributors) {
        contributorsRepository.insertContributor(contributor)
    }

    suspend fun getLastInsertedContributorId(): Long {
        return contributorsRepository.getLastInsertedRowId()
    }

    suspend fun insertBookContributor(map: Books_Contributors_Map) {
        contributorsRepository.insertBookContributor(map)
    }

    suspend fun updateBook(
        id: Long,
        book: Books
    ) {
        booksRepository.updateBook(id, book)
    }

    suspend fun getContributorsByBookId(bookId: Long) = contributorsRepository.getContributorsByBookId(bookId)

    suspend fun deleteBookContributorMapping(
        bookId: Long,
        contributorId: Long
    ) {
        contributorsRepository.deleteBookContributorMapping(bookId, contributorId)
    }
}