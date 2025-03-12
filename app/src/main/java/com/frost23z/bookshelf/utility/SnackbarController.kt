package com.frost23z.bookshelf.utility

import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SnackbarController {
	private val _events = MutableSharedFlow<SnackbarEvent>(replay = 0)
	val events = _events.asSharedFlow()

	suspend fun sendSnackbarEvent(event: SnackbarEvent) {
		_events.emit(event)
	}
}

data class SnackbarEvent(
	val message: String,
	val actionLabel: String? = null,
	val action: (() -> Unit)? = null,
	val dismissAction: Boolean = false,
	val duration: SnackbarDuration = SnackbarDuration.Short
)