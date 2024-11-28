package com.frost23z.bookshelf.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.frost23z.bookshelf.domain.repository.TagsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TagsRepositoryImpl(
    private val db: AppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TagsRepository {
    override suspend fun getTagById(id: Long): Tags {
        return db.tagsQueries.getTagById(id).executeAsOne()
    }

    override suspend fun getTagByName(name: String): Long? {
        return db.tagsQueries.getTagByName(name.trim()).executeAsOneOrNull()
    }

    override suspend fun getAllTags(): List<Tags> {
        return db.tagsQueries.getAllTags().executeAsList()
    }

    override fun getAllTagsAsFlow(): Flow<List<Tags>> {
        return db.tagsQueries
            .getAllTags()
            .asFlow()
            .mapToList(dispatcher)
    }

    override suspend fun getLastInsertedRowId(): Long {
        return db.tagsQueries.getLastInsertedRowId().executeAsOne()
    }

    override suspend fun insertTag(tag: Tags) {
        db.tagsQueries.insertTag(name = tag.name)
    }

    override suspend fun updateTag(tag: Tags) {
        db.tagsQueries.updateTag(name = tag.name, id = tag.id)
    }

    override suspend fun deleteTag(id: Long) {
        db.tagsQueries.deleteTag(id)
    }

    override suspend fun getTagsByBook(bookId: Long): List<Tags> {
        return db.books_Tags_MapQueries.getTagsByBookId(bookId).executeAsList()
    }

    override suspend fun getBooksByTag(tagId: Long): List<Books> {
        return db.books_Tags_MapQueries.getBooksByTagId(tagId).executeAsList()
    }

    override suspend fun insertBookTag(
        bookId: Long,
        tagId: Long
    ) {
        db.books_Tags_MapQueries.insertBookTag(bookId, tagId)
    }

    override suspend fun deleteBookTagByBook(bookId: Long) {
        db.books_Tags_MapQueries.deleteBookTagByBookId(bookId)
    }

    override suspend fun deleteBookTagByTag(tagId: Long) {
        db.books_Tags_MapQueries.deleteBookTagByTagId(tagId)
    }

    override suspend fun deleteBookTag(
        bookId: Long,
        tagId: Long
    ) {
        db.books_Tags_MapQueries.deleteBookTag(bookId, tagId)
    }
}