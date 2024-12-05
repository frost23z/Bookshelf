package com.frost23z.bookshelf

import android.app.Application
import com.frost23z.bookshelf.di.appModule
import com.frost23z.bookshelf.di.domainModule
import com.frost23z.bookshelf.di.preferenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinAppDeclaration

@OptIn(KoinExperimentalAPI::class)
class App : Application(), KoinStartup {
    override fun onKoinStartup(): KoinAppDeclaration = {
        androidContext(this@App)
        modules(appModule, domainModule, preferenceModule)
    }
}