package com.frost23z.bookshelf.ui.detail.components

import androidx.lifecycle.viewModelScope
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenModel : ScreenModel<DetailScreenState>(DetailScreenState()) {
	fun onEvent(event: DetailScreenEvent) {
		// Handle events
	}

	private fun updateState(update: DetailScreenState.() -> DetailScreenState) {
		mutableState.update { it.update() }
	}

	init {
		viewModelScope.launch {
			// Fetch data
		}
	}
}