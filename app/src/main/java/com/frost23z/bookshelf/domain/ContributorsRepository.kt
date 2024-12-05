package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Contributors
import kotlinx.coroutines.flow.Flow

interface ContributorsRepository {
    suspend fun getContributorById(id: Long): Contributors

    suspend fun getContributorByName(contributorName: String): Long?

    suspend fun getAllContributors(): List<Contributors>

    fun getAllContributorsAsFlow(): Flow<List<Contributors>>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertContributor(contributor: Contributors)

    suspend fun updateContributor(contributor: Contributors)

    suspend fun deleteContributor(id: Long)
}