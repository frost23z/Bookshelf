package com.frost23z.bookshelf.data.repositories

import android.util.Log
import com.frost23z.bookshelf.data.AppDatabase
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.domain.repositories.AddEditRepository
import kotlinx.coroutines.CoroutineDispatcher

class AddEditRepositoryImpl(
	private val db: AppDatabase,
	private val dispatcher: CoroutineDispatcher
) : AddEditRepository {
	override suspend fun insert(
		books: Books,
		publisher: String,
	) {
		db.transaction {
			db.insertQueries.insertPublisher(publisher)
			val publisherId = db.selectByNameQueries.getPublisherId(publisher).executeAsOneOrNull()
				?: db.selectByIdQueries.getLastInsertId().executeAsOne()
			db.insertQueries.insertBook(
				shelfId = books.shelfId,
				isFavorite = books.isFavorite,
				dateAdded = books.dateAdded,
				dateLastUpdated = books.dateLastUpdated,
				titlePrefix = books.titlePrefix,
				title = books.title,
				subtitle = books.subtitle,
				coverUri = books.coverUri,
				description = books.description,
				publisherId = publisherId,
				publicationDate = books.publicationDate,
				languageId = books.languageId,
				totalPages = books.totalPages,
				format = books.format,
				acquiredFromId = books.acquiredFromId,
				acquiredDate = books.acquiredDate,
				purchasePrice = books.purchasePrice,
				readStatus = books.readStatus,
				readPages = books.readPages,
				startReadingDate = books.startReadingDate,
				finishedReadingDate = books.finishedReadingDate,
				additionalReadingDates = books.additionalReadingDates,
				seriesId = books.seriesId,
				volume = books.volume,
				loan = books.loan,
				loanToOrFrom = books.loanToOrFrom,
				loanedDate = books.loanedDate,
				expectedReturnDate = books.expectedReturnDate,
				notes = books.notes,
				quotes = books.quotes,
			)
			Log.d("AddEditRepositoryImpl", "Inserted book with id: ${db.selectByIdQueries.getLastInsertId().executeAsOne()}")
		}
	}
}