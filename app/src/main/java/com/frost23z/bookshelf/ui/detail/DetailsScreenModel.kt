package com.frost23z.bookshelf.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.books
import com.frost23z.bookshelf.domain.BooksRepository
import com.frost23z.bookshelf.domain.ContributorsRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenModel(
    private val bookId: Long,
    private val booksRepository: BooksRepository,
    private val contributorsRepository: ContributorsRepository
) : StateScreenModel<DetailsScreenModel.State>(State()) {
    data class State(
        val book: Books = books,
        val contributors: Map<String, List<String>> = emptyMap(),
        val showDeleteConfirmationDialog: Boolean = false,
        val showCoverDialog: Boolean = false
    )

    init {
        loadDetails()
    }

    fun loadDetails() {
        screenModelScope.launch {
            val getContributors = contributorsRepository.getContributorsByBookId(state.value.book.id)
            mutableState.update { state ->
                state.copy(
                    book = booksRepository.getBookById(bookId),
                    contributors =
                        getContributors.groupBy(
                            keySelector = { it.role },
                            valueTransform = { it.name }
                        )
                )
            }
        }
    }

    fun deleteBook() {
        screenModelScope.launch { booksRepository.deleteBook(bookId) }
    }

    fun toggleDeleteConfirmationDialog() {
        mutableState.update { it.copy(showDeleteConfirmationDialog = !it.showDeleteConfirmationDialog) }
    }

    fun toggleCoverDialog() {
        mutableState.update { it.copy(showCoverDialog = !it.showCoverDialog) }
    }
}