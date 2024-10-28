package com.frost23z.bookshelf.domain.repository

import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
import kotlinx.coroutines.flow.Flow

interface ContributorsRepository {
    suspend fun getContributorById(id: Long): Contributors

    suspend fun getContributorIdByName(contributorName: String): Long?

    suspend fun getAllContributors(): List<Contributors>

    fun getAllContributorsAsFlow(): Flow<List<Contributors>>

    suspend fun getLastInsertedContributorRowId(): Long

    suspend fun insertContributor(contributor: Contributors)

    suspend fun updateContributorById(contributor: Contributors)

    suspend fun deleteContributorById(id: Long)

    //  mapper section

    suspend fun getContributorsByBookId(bookId: Long): List<Books_Contributors_Map>

    suspend fun getAllMapping(): List<Books_Contributors_Map>

    suspend fun insertBookContributor(map: Books_Contributors_Map)

    suspend fun deleteBookContributorByBookId(bookId: Long)

    suspend fun deleteBookContributorByContributorId(contributorId: Long)

    suspend fun deleteBookContributorByBookIdAndContributorId(bookId: Long, contributorId: Long)
}