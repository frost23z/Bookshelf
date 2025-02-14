package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.ui.addedit.components.AddEditScreenModel
import com.frost23z.bookshelf.ui.library.components.LibraryScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val uiModule = module {
	factoryOf(::LibraryScreenModel)
	factoryOf(::AddEditScreenModel)
}