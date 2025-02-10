package com.frost23z.bookshelf.ui.core.models

import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.domain.models.Loan

fun booksInit() = Books(
	id = 0,
	shelfId = null,
	isFavorite = false,
	dateAdded = 0,
	dateLastUpdated = 0,
	titlePrefix = null,
	title = "",
	subtitle = null,
	coverUri = null,
	description = null,
	publisherId = null,
	publicationDate = null,
	languageId = null,
	totalPages = null,
	format = null,
	acquiredFromId = null,
	acquiredDate = null,
	purchasePrice = null,
	readStatus = null,
	readPages = null,
	startReadingDate = null,
	finishedReadingDate = null,
	additionalReadingDates = null,
	seriesId = null,
	volume = null,
	loan = Loan.NONE,
	loanToOrFrom = null,
	loanedDate = null,
	expectedReturnDate = null,
	notes = null,
	quotes = null
)