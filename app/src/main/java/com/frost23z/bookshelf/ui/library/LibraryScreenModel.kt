package com.frost23z.bookshelf.ui.library

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.domain.BooksRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryScreenModel(
    private val booksRepository: BooksRepository
) : StateScreenModel<LibraryScreenModel.State>(State()) {
    init {
        screenModelScope.launch {
            booksRepository.getAllBooksAsFlow().collect { books ->
                mutableState.update { state ->
                    state.copy(
                        isLoading = false,
                        library = books,
                        filteredLibrary = books
                    )
                }
            }
        }
    }

    data class State(
        val library: List<Books> = emptyList(),
        val filteredLibrary: List<Books> = emptyList(),
        val selectedBooks: List<Long> = emptyList(),
        val isLoading: Boolean = true,
        val searchQuery: String? = null,
        var selectionCounter: Int = 0
    )

    fun onSearchQueryChange(searchQuery: String?) {
        screenModelScope.launch {
            mutableState.update { state ->
                state.copy(
                    searchQuery = searchQuery,
                    filteredLibrary = filterBooks(state.library, searchQuery)
                )
            }
        }
    }

    private fun filterBooks(
        books: List<Books>,
        query: String?
    ): List<Books> {
        return if (query.isNullOrEmpty()) {
            books
        } else {
            books.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    fun onCancelSelection() {
        screenModelScope.launch {
            mutableState.update { state ->
                state.copy(selectionCounter = 0, selectedBooks = emptyList())
            }
        }
    }

    fun toggleSelection(bookId: Long) {
        mutableState.update { state ->
            val updatedSelectedBooks =
                if (state.selectedBooks.contains(bookId)) {
                    state.selectedBooks - bookId
                } else {
                    state.selectedBooks + bookId
                }
            state.copy(
                selectedBooks = updatedSelectedBooks,
                selectionCounter = updatedSelectedBooks.size
            )
        }
    }

    fun onClickSelectAll() {
        screenModelScope.launch {
            mutableState.update { state ->
                val updatedSelectedBooks =
                    if (state.selectedBooks.size == state.library.size) {
                        emptyList()
                    } else {
                        state.library.map { it.id }
                    }
                state.copy(
                    selectedBooks = updatedSelectedBooks,
                    selectionCounter = updatedSelectedBooks.size
                )
            }
        }
    }

    fun onClickInvertSelection() {
        screenModelScope.launch {
            mutableState.update { state ->
                val updatedSelectedBooks =
                    state.library.map { it.id } - state.selectedBooks.toSet()
                state.copy(
                    selectedBooks = updatedSelectedBooks,
                    selectionCounter = updatedSelectedBooks.size
                )
            }
        }
    }
}