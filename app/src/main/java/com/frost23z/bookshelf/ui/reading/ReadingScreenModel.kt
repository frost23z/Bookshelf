package com.frost23z.bookshelf.ui.reading

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.ReadStatus
import com.frost23z.bookshelf.domain.repository.BooksRepository
import com.frost23z.bookshelf.ui.core.util.SnackbarController
import com.frost23z.bookshelf.ui.core.util.SnackbarEvent
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ReadingScreenModel(
    private val repository: BooksRepository,
) : StateScreenModel<ReadingScreenModel.State>(State()) {
    data class State(
        val readingBooks: List<Books> = emptyList()
    )

    init {
        screenModelScope.launch {
            repository.getAllBooksAsFlow().collect { books ->
                mutableState.update { state ->
                    state.copy(
                        readingBooks = books.filter { it.readStatus.equals(ReadStatus.READING.value) }
                    )
                }
            }
        }
    }

    fun updateBookProgress(
        bookId: Long,
        newProgress: Long
    ) {
        screenModelScope.launch {
            val book = repository.getBookById(bookId)
            val totalPages = book.pages ?: 0L
            val oldBook: Books = book

            val newStatus =
                when {
                    newProgress >= totalPages -> ReadStatus.READ.value
                    newProgress > 0 -> ReadStatus.READING.value
                    else -> ReadStatus.UNREAD.value
                }

            val currentTime = Clock.System.now().toEpochMilliseconds()

            val updatedBook =
                book.copy(
                    readPages = newProgress,
                    readStatus = newStatus,
                    startReadingDate =
                        if (newStatus == ReadStatus.READING.value && book.startReadingDate == 0L) {
                            currentTime
                        } else {
                            book.startReadingDate
                        },
                    finishedReadingDate =
                        when (newStatus) {
                            ReadStatus.READ.value -> currentTime
                            ReadStatus.UNREAD.value -> 0L
                            else -> book.finishedReadingDate
                        }
                )

            val message =
                when (newStatus) {
                    ReadStatus.READ.value -> "Marked as read"
                    ReadStatus.UNREAD.value -> "Marked as unread"
                    else -> "Progress updated"
                }

            if (newStatus == ReadStatus.READ.value || newStatus == ReadStatus.UNREAD.value) {
                val bookForUndo = oldBook

                repository.updateBook(id = bookId, book = updatedBook)

                SnackbarController.sendSnackbarEvent(
                    event =
                        SnackbarEvent(
                            message = message,
                            actionLabel = "Undo",
                            action = {
                                screenModelScope.launch {
                                    repository.updateBook(id = bookId, book = bookForUndo)
                                }
                            }
                        )
                )
            } else {
                repository.updateBook(id = bookId, book = updatedBook)
            }
        }
    }
}