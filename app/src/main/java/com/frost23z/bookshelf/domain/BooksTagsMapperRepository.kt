package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.BooksTagsMapper
import com.frost23z.bookshelf.data.Tags

interface BooksTagsMapperRepository {
    suspend fun getTagsByBookId(bookId: Long): List<Tags>

    suspend fun getBooksByTagId(tagId: Long): List<Books>

    suspend fun getAllBookTags(): List<BooksTagsMapper>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertBookTag(
        bookId: Long,
        tagId: Long
    )

    suspend fun deleteBookTagsByBook(bookId: Long)

    suspend fun deleteBookTagsByTag(tagId: Long)

    suspend fun deleteBookTagMapping(
        bookId: Long,
        tagId: Long
    )
}