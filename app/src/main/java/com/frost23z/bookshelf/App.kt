package com.frost23z.bookshelf

import android.app.Application
import com.frost23z.bookshelf.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup.onKoinStartup

class App: Application() {

    init {
        onKoinStartup {
            androidContext(this@App)
            modules(appModule)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}