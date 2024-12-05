package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.domain.TagsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TagsRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TagsRepository {
    override suspend fun getTagById(id: Long): Tags = db.tagsQueries.getTagById(id).executeAsOne()

    override suspend fun getTagByName(name: String): Long? = db.tagsQueries.getTagByName(name.trim()).executeAsOneOrNull()

    override suspend fun getAllTags(): List<Tags> = db.tagsQueries.getAllTags().executeAsList()

    override fun getAllTagsAsFlow(): Flow<List<Tags>> = db.tagsQueries
        .getAllTags()
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun getLastInsertedRowId(): Long = db.tagsQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertTag(tag: Tags) {
        db.tagsQueries.insertTag(name = tag.name)
    }

    override suspend fun updateTag(tag: Tags) {
        db.tagsQueries.updateTag(name = tag.name, id = tag.id)
    }

    override suspend fun deleteTag(id: Long) {
        db.tagsQueries.deleteTag(id)
    }
}