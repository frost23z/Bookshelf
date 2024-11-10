package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.data.BooksRepositoryImpl
import com.frost23z.bookshelf.data.ContributorsRepositoryImpl
import com.frost23z.bookshelf.domain.interactor.AddBook
import com.frost23z.bookshelf.domain.interactor.GetDetails
import com.frost23z.bookshelf.domain.interactor.GetLibraryBooks
import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.domain.repository.ContributorsRepository
import com.frost23z.bookshelf.ui.addedit.AddEditScreenModel
import com.frost23z.bookshelf.ui.detail.DetailsScreenModel
import com.frost23z.bookshelf.ui.home.HomeScreenModel
import com.frost23z.bookshelf.ui.library.LibraryScreenModel
import org.koin.dsl.module

val domainModule =
    module {

        single<BooksRepository> { BooksRepositoryImpl(db = get()) }
        single<ContributorsRepository> { ContributorsRepositoryImpl(db = get()) }

        factory { AddBook(booksRepository = get(), contributorsRepository = get()) }
        factory { GetLibraryBooks(booksRepository = get()) }
        factory { GetDetails(booksRepository = get(), contributorsRepository = get()) }

        factory { HomeScreenModel() }
        factory { LibraryScreenModel(getLibraryBooks = get()) }
        factory { DetailsScreenModel(bookId = get(), getDetails = get()) }
        factory { (isEditing: Boolean, bookId: Long?) -> AddEditScreenModel(addBook = get(), isEditing = isEditing, bookId = bookId) }
    }