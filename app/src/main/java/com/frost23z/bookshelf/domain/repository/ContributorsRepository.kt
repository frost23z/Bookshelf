package com.frost23z.bookshelf.domain.repository

import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
import com.frost23z.bookshelf.data.GetContributorsByBookId
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

    // mapper section
    suspend fun getContributorsByBookId(bookId: Long): List<GetContributorsByBookId>
    suspend fun getBooksByContributorId(contributorId: Long): List<GetBooksByContributorId>
    suspend fun getAllBookContributors(): List<Books_Contributors_Map>
    suspend fun insertBookContributor(map: Books_Contributors_Map)
    suspend fun deleteBookContributorsByBook(bookId: Long)
    suspend fun deleteBookContributorsByContributor(contributorId: Long)
    suspend fun deleteBookContributorMapping(
        bookId: Long,
        contributorId: Long
    )
}