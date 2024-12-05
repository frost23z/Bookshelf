package com.frost23z.bookshelf.domain

import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.BooksShelvesMapper
import com.frost23z.bookshelf.data.Shelves

interface BooksShelvesMapperRepository {
    suspend fun getShelvesByBookId(bookId: Long): List<Shelves>

    suspend fun getBooksByShelfId(shelfId: Long): List<Books>

    suspend fun getAllBookShelves(): List<BooksShelvesMapper>

    suspend fun getLastInsertedRowId(): Long

    suspend fun insertBookShelf(
        bookId: Long,
        shelfId: Long
    )

    suspend fun deleteBookShelvesByBook(bookId: Long)

    suspend fun deleteBookShelvesByShelf(shelfId: Long)

    suspend fun deleteBookShelfMapping(
        bookId: Long,
        shelfId: Long
    )
}