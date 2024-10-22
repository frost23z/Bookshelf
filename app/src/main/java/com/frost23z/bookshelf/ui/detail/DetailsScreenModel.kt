package com.frost23z.bookshelf.ui.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import com.frost23z.bookshelf.data.Books

class DetailsScreenModel(
    book: Books
) : StateScreenModel<DetailsScreenModel.State>(State(book = book)) {
    data class State(
        val book: Books
    )
}