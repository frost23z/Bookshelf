package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Shelves
import kotlinx.coroutines.flow.Flow

interface ShelvesRepository {
    suspend fun getShelfById(id: Long): Shelves

    suspend fun getShelfByName(name: String): Long?

    suspend fun getAllShelves(): List<Shelves>

    fun getAllShelvesAsFlow(): Flow<List<Shelves>>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertShelf(shelf: Shelves)

    suspend fun updateShelf(shelf: Shelves)

    suspend fun deleteShelf(id: Long)
}