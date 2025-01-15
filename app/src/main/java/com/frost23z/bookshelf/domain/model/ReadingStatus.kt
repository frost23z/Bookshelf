package com.frost23z.bookshelf.domain.model

enum class ReadingStatus(val value: String) {
	NOT_STARTED("Not started"),
	IN_PROGRESS("In progress"),
	COMPLETED("Completed"),
	ON_HOLD("On hold"),
	DROPPED("Dropped")
}