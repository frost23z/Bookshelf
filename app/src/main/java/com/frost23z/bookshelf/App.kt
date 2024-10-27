package com.frost23z.bookshelf

import android.app.Application
import com.frost23z.bookshelf.di.appModule
import com.frost23z.bookshelf.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup.onKoinStartup
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
class App : Application() {

    init {
        onKoinStartup {
            androidContext(this@App)
            modules(appModule, domainModule)
        }
    }
}