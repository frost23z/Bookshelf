package com.frost23z.bookshelf.data

import com.frost23z.bookshelf.domain.BooksContributorsMapperRepository

class BooksContributorsMapperRepositoryImpl(
    private val db: AppDatabase
) : BooksContributorsMapperRepository {
    override suspend fun getContributorsByBookId(bookId: Long): List<BooksContributorsMapper> =
        db.booksContributorsMapperQueries.getContributorsByBookId(bookId).executeAsList()

    override suspend fun getBooksByContributorId(contributorId: Long): List<Books> = db.booksContributorsMapperQueries
        .getBooksByContributorId(contributorId)
        .executeAsList()

    override suspend fun getAllBookContributors(): List<BooksContributorsMapper> =
        db.booksContributorsMapperQueries.getAllBookContributors().executeAsList()

    override suspend fun getLastInsertedRowId(): Long = db.booksContributorsMapperQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertBookContributor(map: BooksContributorsMapper) = db.booksContributorsMapperQueries.insertBookContributor(
        bookId = map.bookId,
        contributorId = map.contributorId,
        role = map.role
    )

    override suspend fun deleteBookContributorsByBook(bookId: Long) = db.booksContributorsMapperQueries.deleteBookContributorsByBook(bookId)

    override suspend fun deleteBookContributorsByContributor(contributorId: Long) =
        db.booksContributorsMapperQueries.deleteBookContributorsByContributor(contributorId)

    override suspend fun deleteBookContributorMapping(
        bookId: Long,
        contributorId: Long
    ) = db.booksContributorsMapperQueries.deleteBookContributorMapping(
        bookId = bookId,
        contributorId = contributorId
    )
}