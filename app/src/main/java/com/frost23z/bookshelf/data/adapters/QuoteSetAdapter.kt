package com.frost23z.bookshelf.data.adapters

import app.cash.sqldelight.ColumnAdapter

object QuoteSetAdapter : ColumnAdapter<Set<Pair<Int, String>>, String> {
	override fun decode(databaseValue: String): Set<Pair<Int, String>> = if (databaseValue.isEmpty()) {
		emptySet()
	} else {
		databaseValue
			.split(AdapterConstants.ENTRY_SEPARATOR)
			.mapNotNull { entry ->
				runCatching {
					val (page, quote) = entry.split(AdapterConstants.PAIR_SEPARATOR)
					page.toInt() to quote
				}.getOrNull()
			}.toSet()
	}

	override fun encode(value: Set<Pair<Int, String>>): String =
		value.joinToString(separator = AdapterConstants.ENTRY_SEPARATOR) { (page, quote) ->
			"$page${AdapterConstants.PAIR_SEPARATOR}$quote"
		}
}