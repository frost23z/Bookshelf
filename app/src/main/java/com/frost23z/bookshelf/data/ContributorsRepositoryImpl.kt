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
        TODO("Not yet implemented")
    }

    override suspend fun getContributorIdByName(contributorName: String): Long? {
        return db.contributorsQueries
            .getContributorsIdByName(contributorName.trim())
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

    override suspend fun getLastInsertedContributorRowId(): Long {
        return db.contributorsQueries.getLastInsertedRowId().executeAsOne()
    }

    override suspend fun insertContributor(contributor: Contributors) {
        db.contributorsQueries.insertContributor(name = contributor.name)
    }

    override suspend fun updateContributorById(contributor: Contributors) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContributorById(id: Long) {
        TODO("Not yet implemented")
    }

    //  mapper section

    override suspend fun getContributorsByBookId(bookId: Long): List<Books_Contributors_Map> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMapping(): List<Books_Contributors_Map> {
        return db.books_Contributors_MapQueries.getAll().executeAsList()
    }

    override suspend fun insertBookContributor(map: Books_Contributors_Map) {
        db.books_Contributors_MapQueries.insertBookContributor(
            bookId = map.book_id,
            contributorId = map.contributor_id,
            role = map.role
        )
    }

    override suspend fun deleteBookContributorByBookId(bookId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBookContributorByContributorId(contributorId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBookContributorByBookIdAndContributorId(
        bookId: Long,
        contributorId: Long
    ) {
        TODO("Not yet implemented")
    }
}