package com.frost23z.bookshelf.ui.library

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.domain.interactor.GetLibraryBooks
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryScreenModel(
    private val getLibraryBooks: GetLibraryBooks
) : StateScreenModel<LibraryScreenModel.State>(State()) {
    init {
        screenModelScope.launch {
            getLibraryBooks.getAllBooksAsFlow().collect { books ->
                mutableState.update { state ->
                    state.copy(
                        isLoading = false,
                        library = books
                    )
                }
            }
        }
    }

    data class State(
        val library: List<Books> = emptyList(),
        val isLoading: Boolean = true
    )
}