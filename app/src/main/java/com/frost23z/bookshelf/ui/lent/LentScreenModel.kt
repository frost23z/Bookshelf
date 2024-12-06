package com.frost23z.bookshelf.ui.lent

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.domain.BooksRepository
import com.frost23z.bookshelf.ui.core.util.SnackbarController
import com.frost23z.bookshelf.ui.core.util.SnackbarEvent
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LentScreenModel(
    private val booksRepository: BooksRepository
) : StateScreenModel<LentScreenModel.State>(State()) {
    init {
        screenModelScope.launch {
            booksRepository.getAllBooksAsFlow().collect { books ->
                mutableState.update { state ->
                    state.copy(lentBooks = books.filter { it.isLent })
                }
            }
        }
    }

    fun returnBook(bookId: Long) {
        screenModelScope.launch {
            booksRepository.updateBook(
                id = bookId,
                book =
                    booksRepository.getBookById(bookId).copy(
                        isLent = false,
                        lentTo = null,
                        lentDate = null,
                        lentReturned = null
                    )
            )

            SnackbarController.sendSnackbarEvent(
                event =
                    SnackbarEvent(
                        message = "Book marked as returned"
                    )
            )
        }
    }

    data class State(
        val lentBooks: List<Books> = emptyList()
    )
}
