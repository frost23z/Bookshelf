package com.frost23z.bookshelf.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
import com.frost23z.bookshelf.domain.interactor.Detail
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsScreenModel(
    book: Books,
    detail: Detail
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
            val contributors = detail.getAllContributors()
            val mapping = detail.getAllMapping()
            mutableState.update { state ->
                state.copy(
                    contributors = contributors,
                    mapping = mapping
                )
            }
        }
    }
}