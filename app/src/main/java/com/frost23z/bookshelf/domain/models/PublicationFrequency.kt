package com.frost23z.bookshelf.domain.models

enum class PublicationFrequency(val value: String) {
	NONE("None"),
	WEEKLY("Weekly"),
	MONTHLY("Monthly"),
	BIMONTHLY("Bi-monthly"),
	QUARTERLY("Quarterly"),
	YEARLY("Yearly")
}