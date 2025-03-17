package com.frost23z.bookshelf.di

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.frost23z.bookshelf.data.AppDatabase
import com.frost23z.bookshelf.data.adapters.LocalDateAdapter
import com.frost23z.bookshelf.data.adapters.LocalDateListAdapter
import com.frost23z.bookshelf.data.adapters.QuoteSetAdapter
import com.frost23z.bookshelf.data.adapters.UriAdapter
import com.frost23z.bookshelf.domain.models.AcquiredFrom
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.domain.models.MapperBooksContributors
import com.frost23z.bookshelf.domain.models.Series
import com.frost23z.bookshelf.ui.core.navigation.DefaultNavigator
import com.frost23z.bookshelf.ui.core.navigation.Destination
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
	single<SqlDriver> {
		AndroidSqliteDriver(
			schema = AppDatabase.Schema,
			context = get(),
			name = "bookshelf.db",
			callback = object : AndroidSqliteDriver.Callback(AppDatabase.Schema) {
				override fun onOpen(db: SupportSQLiteDatabase) {
					db.setForeignKeyConstraintsEnabled(true)
				}
			}
		)
	}
	single {
		AppDatabase(
			driver = get(),
			acquiredFromAdapter = AcquiredFrom.Adapter(
				acquisitionTypeAdapter = EnumColumnAdapter()
			),
			booksAdapter = Books.Adapter(
				coverUriAdapter = UriAdapter,
				publicationDateAdapter = LocalDateAdapter,
				formatAdapter = EnumColumnAdapter(),
				acquiredDateAdapter = LocalDateAdapter,
				readStatusAdapter = EnumColumnAdapter(),
				startReadingDateAdapter = LocalDateAdapter,
				finishedReadingDateAdapter = LocalDateAdapter,
				additionalReadingDatesAdapter = LocalDateListAdapter,
				loanAdapter = EnumColumnAdapter(),
				loanedDateAdapter = LocalDateAdapter,
				expectedReturnDateAdapter = LocalDateAdapter,
				quotesAdapter = QuoteSetAdapter
			),
			mapperBooksContributorsAdapter = MapperBooksContributors.Adapter(
				roleAdapter = EnumColumnAdapter()
			),
			seriesAdapter = Series.Adapter(
				firstPublicationDateAdapter = LocalDateAdapter
			)
		)
	}

	single<Navigator> { DefaultNavigator(startDestination = Destination.LibraryGraph) }

	single<CoroutineDispatcher> { Dispatchers.IO }
}