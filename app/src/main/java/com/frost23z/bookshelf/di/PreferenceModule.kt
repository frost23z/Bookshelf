package com.frost23z.bookshelf.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.frost23z.bookshelf.ui.more.ThemeViewModel
import com.frost23z.bookshelf.ui.theme.ThemePreference
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val preferenceModule =
    module {
        single { createDataStore(context = get()) }
        single<ThemePreference> { ThemePreference(get()) }
        viewModel { ThemeViewModel(get()) }
    }

fun createDataStore(context: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("theme_preferences")
    }
}