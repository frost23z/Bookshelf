package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.BooksContributorsMapper

interface BooksContributorsMapperRepository {
    suspend fun getContributorsByBookId(bookId: Long): List<BooksContributorsMapper>

    suspend fun getBooksByContributorId(contributorId: Long): List<Books>

    suspend fun getAllBookContributors(): List<BooksContributorsMapper>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertBookContributor(map: BooksContributorsMapper)

    suspend fun deleteBookContributorsByBook(bookId: Long)

    suspend fun deleteBookContributorsByContributor(contributorId: Long)

    suspend fun deleteBookContributorMapping(
        bookId: Long,
        contributorId: Long
    )
}