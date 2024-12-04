package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Tags
import kotlinx.coroutines.flow.Flow

interface TagsRepository {
    suspend fun getTagById(id: Long): Tags

    suspend fun getTagByName(name: String): Long?

    suspend fun getAllTags(): List<Tags>

    fun getAllTagsAsFlow(): Flow<List<Tags>>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertTag(tag: Tags)

    suspend fun updateTag(tag: Tags)

    suspend fun deleteTag(id: Long)

    // Mapping operations
    suspend fun getTagsByBook(bookId: Long): List<Tags>

    suspend fun getBooksByTag(tagId: Long): List<Books>

    suspend fun insertBookTag(
        bookId: Long,
        tagId: Long
    )

    suspend fun deleteBookTagByBook(bookId: Long)

    suspend fun deleteBookTagByTag(tagId: Long)

    suspend fun deleteBookTag(
        bookId: Long,
        tagId: Long
    )
}