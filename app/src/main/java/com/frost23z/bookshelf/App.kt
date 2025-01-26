package com.frost23z.bookshelf

import android.app.Application
import com.frost23z.bookshelf.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
class App : Application(), KoinStartup {
	override fun onKoinStartup(): KoinConfiguration = KoinConfiguration {
		androidContext(this@App)
		modules(appModule)
	}
}