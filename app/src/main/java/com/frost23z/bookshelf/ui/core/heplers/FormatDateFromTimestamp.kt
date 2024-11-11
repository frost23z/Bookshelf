package com.frost23z.bookshelf.ui.core.heplers

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatDateFromTimestamp(timestamp: Long): String {
    return if (timestamp != 0L) {
        try {
            Instant
                .fromEpochMilliseconds(timestamp)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
                .toString()
        } catch (e: Exception) {
            "" // Return an empty string if conversion fails
        }
    } else {
        ""
    }
}