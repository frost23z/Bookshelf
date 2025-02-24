package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.data.repositories.AddEditRepositoryImpl
import com.frost23z.bookshelf.data.repositories.LibraryRepositoryImpl
import com.frost23z.bookshelf.domain.repositories.AddEditRepository
import com.frost23z.bookshelf.domain.repositories.LibraryRepository
import org.koin.dsl.module

val domainModule = module {
	single<AddEditRepository> { AddEditRepositoryImpl(db = get()) }
	single<LibraryRepository> { LibraryRepositoryImpl(db = get()) }
}