package com.frost23z.bookshelf.data

import com.frost23z.bookshelf.domain.BooksTagsMapperRepository

class BooksTagsMapperRepositoryImpl(
    private val db: AppDatabase
) : BooksTagsMapperRepository {
    override suspend fun getTagsByBookId(bookId: Long): List<Tags> = db.booksTagsMapperQueries.getTagsByBookId(bookId).executeAsList()

    override suspend fun getBooksByTagId(tagId: Long): List<Books> = db.booksTagsMapperQueries.getBooksByTagId(tagId).executeAsList()

    override suspend fun getAllBookTags(): List<BooksTagsMapper> = db.booksTagsMapperQueries.getAllBookTags().executeAsList()

    override suspend fun getLastInsertedRowId(): Long = db.booksTagsMapperQueries.getLastInsertedRowId().executeAsOne()

    override suspend fun insertBookTag(
        bookId: Long,
        tagId: Long
    ) = db.booksTagsMapperQueries.insertBookTag(bookId, tagId)

    override suspend fun deleteBookTagsByBook(bookId: Long) = db.booksTagsMapperQueries.deleteBookTagsByBook(bookId)

    override suspend fun deleteBookTagsByTag(tagId: Long) = db.booksTagsMapperQueries.deleteBookTagsByTag(tagId)

    override suspend fun deleteBookTagMapping(
        bookId: Long,
        tagId: Long
    ) = db.booksTagsMapperQueries.deleteBookTagMapping(bookId, tagId)
}