package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.domain.ShelvesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ShelvesRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ShelvesRepository {
    override suspend fun getShelfById(id: Long): Shelves = db.shelvesQueries.getShelfById(id).executeAsOne()

    override suspend fun getShelfByName(name: String): Long? = db.shelvesQueries.getShelfByName(name.trim()).executeAsOneOrNull()

    override suspend fun getAllShelves(): List<Shelves> = db.shelvesQueries.getAllShelves().executeAsList()

    override fun getAllShelvesAsFlow(): Flow<List<Shelves>> = db.shelvesQueries
        .getAllShelves()
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun getLastInsertedRowId(): Long = db.shelvesQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertShelf(shelf: Shelves) = db.shelvesQueries.insertShelf(name = shelf.name)

    override suspend fun updateShelf(shelf: Shelves) = db.shelvesQueries.updateShelf(name = shelf.name, id = shelf.id)

    override suspend fun deleteShelf(id: Long) = db.shelvesQueries.deleteShelf(id)
}