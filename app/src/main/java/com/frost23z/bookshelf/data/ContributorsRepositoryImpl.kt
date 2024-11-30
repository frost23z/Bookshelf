package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.domain.repository.ContributorsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ContributorsRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ContributorsRepository {
    override suspend fun getContributorById(id: Long): Contributors {
        return db.contributorsQueries.getContributorById(id).executeAsOne()
    }

    override suspend fun getContributorByName(contributorName: String): Long? {
        return db.contributorsQueries
            .getContributorByName(contributorName.trim())
            .executeAsOneOrNull()
    }

    override suspend fun getAllContributors(): List<Contributors> {
        return db.contributorsQueries.getAllContributors().executeAsList()
    }

    override fun getAllContributorsAsFlow(): Flow<List<Contributors>> {
        return db.contributorsQueries
            .getAllContributors()
            .asFlow()
            .mapToList(dispatcher)
    }

    override suspend fun getLastInsertedRowId(): Long {
        return db.contributorsQueries.getLastInsertedRowId().executeAsOne()
    }

    override suspend fun insertContributor(contributor: Contributors) {
        db.contributorsQueries.insertContributor(name = contributor.name)
    }

    override suspend fun updateContributor(contributor: Contributors) {
        db.contributorsQueries.updateContributor(
            id = contributor.id,
            name = contributor.name
        )
    }

    override suspend fun deleteContributor(id: Long) {
        db.contributorsQueries.deleteContributor(id)
    }

    //  mapper section

    override suspend fun getContributorsByBookId(bookId: Long): List<GetContributorsByBookId> {
        return db.books_Contributors_MapQueries.getContributorsByBookId(bookId).executeAsList()
    }

    override suspend fun getAllBookContributors(): List<Books_Contributors_Map> {
        return db.books_Contributors_MapQueries.getAllBookContributors().executeAsList()
    }

    override suspend fun getBooksByContributorId(contributorId: Long): List<GetBooksByContributorId> {
        return db.books_Contributors_MapQueries
            .getBooksByContributorId(contributorId)
            .executeAsList()
    }

    override suspend fun insertBookContributor(map: Books_Contributors_Map) {
        db.books_Contributors_MapQueries.insertBookContributor(
            bookId = map.book_id,
            contributorId = map.contributor_id,
            role = map.role
        )
    }

    override suspend fun deleteBookContributorsByBook(bookId: Long) {
        db.books_Contributors_MapQueries.deleteBookContributorsByBook(bookId)
    }

    override suspend fun deleteBookContributorsByContributor(contributorId: Long) {
        db.books_Contributors_MapQueries.deleteBookContributorsByContributor(contributorId)
    }

    override suspend fun deleteBookContributorMapping(
        bookId: Long,
        contributorId: Long
    ) {
        db.books_Contributors_MapQueries.deleteBookContributorMapping(
            bookId = bookId,
            contributorId = contributorId
        )
    }
}