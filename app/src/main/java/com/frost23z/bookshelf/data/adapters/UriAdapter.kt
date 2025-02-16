package com.frost23z.bookshelf.data.adapters

import android.net.Uri
import app.cash.sqldelight.ColumnAdapter

object UriAdapter : ColumnAdapter<Uri, String> {
	override fun decode(databaseValue: String): Uri = runCatching {
		Uri.parse(databaseValue)
	}.getOrElse {
		throw IllegalArgumentException("Invalid URI format: $databaseValue")
	}

	override fun encode(value: Uri): String = value.toString()
}