package com.frost23z.bookshelf.ui.detail

import android.util.Log
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.books
import com.frost23z.bookshelf.domain.BooksContributorsMapperRepository
import com.frost23z.bookshelf.domain.BooksRepository
import com.frost23z.bookshelf.domain.ContributorsRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenModel(
    private val bookId: Long,
    private val booksRepository: BooksRepository,
    private val contributorsRepository: ContributorsRepository,
    private val booksContributorsMapperRepository: BooksContributorsMapperRepository
) : StateScreenModel<DetailsScreenModel.State>(State()) {
    data class State(
        val book: Books = books,
        val contributors: Map<String, List<String>> = emptyMap(),
        val showDeleteConfirmationDialog: Boolean = false,
        val showCoverDialog: Boolean = false
    )

    init {
        screenModelScope.launch {
            try {
                val loadedBook = booksRepository.getBookById(bookId)
                mutableState.update { state ->
                    state.copy(book = loadedBook)
                }

                val getContributors = booksContributorsMapperRepository.getContributorsByBookId(bookId)
                val contributorsMap =
                    getContributors.groupBy(
                        keySelector = { it.role },
                        valueTransform = { contributor ->
                            contributorsRepository.getContributorById(contributor.contributorId).name
                        }
                    )

                mutableState.update { state ->
                    state.copy(contributors = contributorsMap)
                }

                Log.d("DetailsScreenModel", "Contributors loaded: $contributorsMap")
            } catch (e: Exception) {
                Log.e("DetailsScreenModel", "Error loading book details", e)
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