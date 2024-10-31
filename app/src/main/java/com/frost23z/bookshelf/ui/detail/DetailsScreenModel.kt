package com.frost23z.bookshelf.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.GetContributorsByBookId
import com.frost23z.bookshelf.domain.interactor.GetDetails
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenModel(
    book: Books,
    getDetails: GetDetails
) : StateScreenModel<DetailsScreenModel.State>(
        State(
            book = book
        )
    ) {
    data class State(
        val book: Books,
        var mapping: List<GetContributorsByBookId> = emptyList()
    )

    init {
        screenModelScope.launch {
            val mapping = getDetails.getContributorsByBookId(state.value.book.id)
            mutableState.update { state ->
                state.copy(
                    mapping = mapping
                )
            }
        }
    }
}