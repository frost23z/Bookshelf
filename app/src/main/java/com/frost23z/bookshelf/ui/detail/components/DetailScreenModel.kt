package com.frost23z.bookshelf.ui.detail.components

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.frost23z.bookshelf.domain.repositories.DetailRepository
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import com.frost23z.bookshelf.ui.core.navigation.Destination
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenModel(
	handle: SavedStateHandle,
	private val navigator: Navigator,
	private val repository: DetailRepository
) : ScreenModel<DetailScreenState>(DetailScreenState()) {
	private val detail = handle.toRoute<Destination.Detail>()
	val id = detail.id

	fun onEvent(event: DetailScreenEvent) {
		when (event) {
			DetailScreenEvent.Back -> {
				viewModelScope.launch {
					navigator.navigateUp()
				}
			}
		}
	}

	private fun updateState(update: DetailScreenState.() -> DetailScreenState) {
		mutableState.update { it.update() }
	}

	init {
		viewModelScope.launch {
			repository.getBook(id).let {
				updateState { copy(book = it) }
			}
		}
	}
}