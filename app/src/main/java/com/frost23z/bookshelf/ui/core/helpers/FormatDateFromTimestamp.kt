package com.frost23z.bookshelf.ui.core.helpers

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatDateFromTimestamp(timestamp: Long?): String {
    return if (timestamp != 0L) {
        try {
            Instant
                .fromEpochMilliseconds(timestamp ?: 0L)
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