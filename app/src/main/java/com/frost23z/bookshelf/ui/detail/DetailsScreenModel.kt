package com.frost23z.bookshelf.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.books
import com.frost23z.bookshelf.domain.interactor.GetDetails
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenModel(
    private val bookId: Long,
    private val getDetails: GetDetails
) : StateScreenModel<DetailsScreenModel.State>(State()) {
    data class State(
        val book: Books = books,
        val contributors: Map<String, List<String>> = emptyMap(),
    )

    init {
        loadDetails()
    }

    fun loadDetails() {
        screenModelScope.launch {
            val getContributors = getDetails.getContributorsByBookId(state.value.book.id)
            mutableState.update { state ->
                state.copy(
                    book = getDetails.getBookById(bookId),
                    contributors =
                        getContributors.groupBy(
                            keySelector = { it.role },
                            valueTransform = { it.name }
                        )
                )
            }
        }
    }
}