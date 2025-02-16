package com.frost23z.bookshelf.data.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate

object LocalDateAdapter : ColumnAdapter<LocalDate, String> {
	override fun decode(databaseValue: String): LocalDate = runCatching {
		LocalDate.parse(databaseValue)
	}.getOrElse {
		throw IllegalArgumentException("Invalid LocalDate format: $databaseValue")
	}

	override fun encode(value: LocalDate): String = value.toString()
}