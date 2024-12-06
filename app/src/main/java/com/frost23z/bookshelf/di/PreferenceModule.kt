package com.frost23z.bookshelf.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.frost23z.bookshelf.preferences.AppearancePreference
import org.koin.dsl.module

val preferenceModule =
    module {
        single { createDataStore(context = get()) }
        single<AppearancePreference> { AppearancePreference(dataStore = get()) }
    }

fun createDataStore(context: Context): DataStore<Preferences> = PreferenceDataStoreFactory.create {
    context.preferencesDataStoreFile("data_store")
}