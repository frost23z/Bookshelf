package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.data.BooksRepositoryImpl
import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.ui.home.HomeScreenModel
import org.koin.dsl.module

val domainModule = module {

    single<BooksRepository> { BooksRepositoryImpl(db = get()) }

    factory { HomeScreenModel() }

}