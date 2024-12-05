package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.domain.ContributorsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ContributorsRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ContributorsRepository {
    override suspend fun getContributorById(id: Long): Contributors = db.contributorsQueries.getContributorById(id).executeAsOne()

    override suspend fun getContributorByName(contributorName: String): Long? = db.contributorsQueries
        .getContributorByName(contributorName.trim())
        .executeAsOneOrNull()

    override suspend fun getAllContributors(): List<Contributors> = db.contributorsQueries.getAllContributors().executeAsList()

    override fun getAllContributorsAsFlow(): Flow<List<Contributors>> = db.contributorsQueries
        .getAllContributors()
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun getLastInsertedRowId(): Long = db.contributorsQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertContributor(contributor: Contributors) = db.contributorsQueries.insertContributor(name = contributor.name)

    override suspend fun updateContributor(contributor: Contributors) = db.contributorsQueries.updateContributor(
        id = contributor.id,
        name = contributor.name
    )

    override suspend fun deleteContributor(id: Long) = db.contributorsQueries.deleteContributor(id)
}