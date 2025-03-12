package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.data.repositories.AddEditRepositoryImpl
import com.frost23z.bookshelf.data.repositories.LibraryRepositoryImpl
import com.frost23z.bookshelf.domain.repositories.AddEditRepository
import com.frost23z.bookshelf.domain.repositories.LibraryRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
	singleOf(::LibraryRepositoryImpl) { bind<LibraryRepository>() }
	singleOf(::AddEditRepositoryImpl) { bind<AddEditRepository>() }
}