package com.frost23z.bookshelf.ui.core.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.domain.models.BookFormat
import com.frost23z.bookshelf.domain.models.LibrarySort
import com.frost23z.bookshelf.domain.models.Loan
import com.frost23z.bookshelf.domain.models.ReadingStatus
import com.frost23z.bookshelf.domain.models.Role

@Composable
fun AcquisitionType.toDisplayString(): String = when (this) {
	AcquisitionType.PURCHASED -> stringResource(R.string.purchased)
	AcquisitionType.GIFT -> stringResource(R.string.gift)
	AcquisitionType.PRIZE -> stringResource(R.string.prize)
}

@Composable
fun BookFormat.toDisplayString(): String = when (this) {
	BookFormat.HARDCOVER -> stringResource(R.string.hardcover)
	BookFormat.PAPERBACK -> stringResource(R.string.paperback)
	BookFormat.EBOOK -> stringResource(R.string.e_book)
	BookFormat.AUDIOBOOK -> stringResource(R.string.audiobook)
	BookFormat.MAGAZINE -> stringResource(R.string.magazine)
	BookFormat.OTHER -> stringResource(R.string.other)
}

@Composable
fun LibrarySort.toDisplayString(): String = when (this) {
	LibrarySort.TITLE -> stringResource(R.string.title)
	LibrarySort.DATE_ADDED -> stringResource(R.string.date_added)
	LibrarySort.DATE_LAST_UPDATED -> stringResource(R.string.date_last_updated)
}

@Composable
fun Loan.toDisplayString(): String = when (this) {
	Loan.NONE -> stringResource(R.string.none)
	Loan.LENT -> stringResource(R.string.lent)
	Loan.BORROWED -> stringResource(R.string.borrowed)
}

@Composable
fun ReadingStatus.toDisplayString(): String = when (this) {
	ReadingStatus.NOT_STARTED -> stringResource(R.string.not_started)
	ReadingStatus.IN_PROGRESS -> stringResource(R.string.in_progress)
	ReadingStatus.COMPLETED -> stringResource(R.string.completed)
	ReadingStatus.ON_HOLD -> stringResource(R.string.on_hold)
	ReadingStatus.DROPPED -> stringResource(R.string.dropped)
}

@Composable
fun Role.toDisplayString(): String = when (this) {
	Role.AUTHOR -> stringResource(R.string.author)
	Role.ILLUSTRATOR -> stringResource(R.string.illustrator)
	Role.TRANSLATOR -> stringResource(R.string.translator)
	Role.EDITOR -> stringResource(R.string.editor)
	Role.PUBLISHER -> stringResource(R.string.publisher)
	Role.OTHER -> stringResource(R.string.other)
}