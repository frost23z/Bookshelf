package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.data.BooksContributorsMapperRepositoryImpl
import com.frost23z.bookshelf.data.BooksRepositoryImpl
import com.frost23z.bookshelf.data.BooksShelvesMapperRepositoryImpl
import com.frost23z.bookshelf.data.BooksTagsMapperRepositoryImpl
import com.frost23z.bookshelf.data.ContributorsRepositoryImpl
import com.frost23z.bookshelf.data.ShelvesRepositoryImpl
import com.frost23z.bookshelf.data.TagsRepositoryImpl
import com.frost23z.bookshelf.domain.BooksContributorsMapperRepository
import com.frost23z.bookshelf.domain.BooksRepository
import com.frost23z.bookshelf.domain.BooksShelvesMapperRepository
import com.frost23z.bookshelf.domain.BooksTagsMapperRepository
import com.frost23z.bookshelf.domain.ContributorsRepository
import com.frost23z.bookshelf.domain.ShelvesRepository
import com.frost23z.bookshelf.domain.TagsRepository
import com.frost23z.bookshelf.ui.addedit.AddEditScreenModel
import com.frost23z.bookshelf.ui.detail.DetailsScreenModel
import com.frost23z.bookshelf.ui.home.HomeScreenModel
import com.frost23z.bookshelf.ui.lent.LentScreenModel
import com.frost23z.bookshelf.ui.library.LibraryScreenModel
import com.frost23z.bookshelf.ui.more.settings.userinterface.UISettingsScreenModel
import com.frost23z.bookshelf.ui.reading.ReadingScreenModel
import org.koin.dsl.module

val domainModule =
    module {

        single<BooksRepository> { BooksRepositoryImpl(db = get()) }
        single<ContributorsRepository> { ContributorsRepositoryImpl(db = get()) }
        single<TagsRepository> { TagsRepositoryImpl(db = get()) }
        single<ShelvesRepository> { ShelvesRepositoryImpl(db = get()) }
        single<BooksContributorsMapperRepository> { BooksContributorsMapperRepositoryImpl(db = get()) }
        single<BooksShelvesMapperRepository> { BooksShelvesMapperRepositoryImpl(db = get()) }
        single<BooksTagsMapperRepository> { BooksTagsMapperRepositoryImpl(db = get()) }

        factory { HomeScreenModel() }
        factory { LibraryScreenModel(booksRepository = get()) }
        factory {
            DetailsScreenModel(
                bookId = get(),
                booksRepository = get(),
                contributorsRepository = get(),
                booksContributorsMapperRepository = get()
            )
        }
        factory { ReadingScreenModel(repository = get()) }
        factory { (isEditing: Boolean, bookId: Long?) ->
            AddEditScreenModel(
                booksRepository = get(),
                contributorsRepository = get(),
                booksContributorsMapperRepository = get(),
                isEditing = isEditing,
                bookId = bookId
            )
        }
        factory { LentScreenModel(booksRepository = get()) }

        // Settings
        factory { UISettingsScreenModel(uiSettingsPreference = get()) }
    }