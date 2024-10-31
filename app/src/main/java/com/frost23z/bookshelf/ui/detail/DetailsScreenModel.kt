package com.frost23z.bookshelf.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
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
        var contributors: List<Contributors> = emptyList(),
        var mapping: List<Books_Contributors_Map> = emptyList()
    )

    init {
        screenModelScope.launch {
            val contributors = getDetails.getAllContributors()
            val mapping = getDetails.getAllMapping()
            mutableState.update { state ->
                state.copy(
                    contributors = contributors,
                    mapping = mapping
                )
            }
        }
    }
}