package com.frost23z.bookshelf.data.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate

object LocalDateListAdapter : ColumnAdapter<List<Pair<LocalDate, LocalDate>>, String> {
	override fun decode(databaseValue: String): List<Pair<LocalDate, LocalDate>> = if (databaseValue.isEmpty()) {
		emptyList()
	} else {
		databaseValue.split(AdapterConstants.ENTRY_SEPARATOR).mapNotNull { entry ->
			runCatching {
				val (first, second) = entry.split(AdapterConstants.PAIR_SEPARATOR)
				LocalDate.parse(first) to LocalDate.parse(second)
			}.getOrNull()
		}
	}

	override fun encode(value: List<Pair<LocalDate, LocalDate>>): String =
		value.joinToString(separator = AdapterConstants.ENTRY_SEPARATOR) { (first, second) ->
			"${first}$AdapterConstants.PAIR_SEPARATOR$second"
		}
}