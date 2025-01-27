package com.frost23z.bookshelf.domain.models

enum class BookFormat(val value: String) {
	HARDCOVER("Hardcover"),
	PAPERBACK("Paperback"),
	EBOOK("E-Book"),
	AUDIOBOOK("Audiobook"),
	MAGAZINE("Magazine"),
	OTHER("Other")
}