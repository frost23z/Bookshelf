package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.data.BooksRepositoryImpl
import com.frost23z.bookshelf.domain.interactor.AddBook
import com.frost23z.bookshelf.domain.interactor.GetLibraryBooks
import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.ui.addedit.AddEditScreenModel
import com.frost23z.bookshelf.ui.detail.DetailsScreenModel
import com.frost23z.bookshelf.ui.home.HomeScreenModel
import com.frost23z.bookshelf.ui.library.LibraryScreenModel
import org.koin.dsl.module

val domainModule = module {

    single<BooksRepository> { BooksRepositoryImpl(db = get()) }

    factory { AddBook(booksRepository = get()) }
    factory { GetLibraryBooks(booksRepository = get()) }

    factory { HomeScreenModel() }
    factory { LibraryScreenModel(getLibraryBooks = get()) }
    factory { AddEditScreenModel(addBook = get()) }

}