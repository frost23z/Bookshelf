package com.frost23z.bookshelf.di

import com.frost23z.bookshelf.ui.addedit.components.AddEditScreenModel
import org.koin.dsl.module

val appModule = module {
	factory { AddEditScreenModel() }
}