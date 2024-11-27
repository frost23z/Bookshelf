package com.frost23z.bookshelf.ui.addedit

import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.util.Log
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
import com.frost23z.bookshelf.data.Roles
import com.frost23z.bookshelf.data.books
import com.frost23z.bookshelf.domain.interactor.AddBook
import com.frost23z.bookshelf.ui.addedit.components.Contributor
import com.frost23z.bookshelf.ui.core.util.SnackbarController
import com.frost23z.bookshelf.ui.core.util.SnackbarEvent
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class AddEditScreenModel(
    private val addBook: AddBook,
    private val isEditing: Boolean,
    private val bookId: Long?
) : StateScreenModel<AddEditScreenModel.State>(State()) {
    data class State(
        val book: Books = books,
        val contributorsMap: MutableMap<Int, Contributor> =
            linkedMapOf(1 to Contributor("", Roles.AUTHOR)),
        val removedContributors: MutableSet<String> = mutableSetOf(),
        val hasUnsavedChanges: Boolean = false,
        val showDiscardDialog: Boolean = false,
        val isSaving: Boolean = true
    )

    init {
        if (isEditing && bookId != null) {
            screenModelScope.launch {
                mutableState.update {
                    it.copy(
                        book = addBook.getBookById(bookId),
                        contributorsMap =
                            addBook
                                .getContributorsByBookId(bookId)
                                .mapIndexed { index, contributor ->
                                    index + 1 to Contributor(contributor.name, Roles.fromValue(contributor.role) ?: Roles.OTHER_CONTRIBUTOR)
                                }.toMap()
                                .toMutableMap(),
                        removedContributors = mutableSetOf(),
                        hasUnsavedChanges = false,
                        showDiscardDialog = false,
                        isSaving = true
                    )
                }
            }
        }
    }

    private fun cleanBookData(book: Books): Books {
        return book.copy(
            titlePrefix = book.titlePrefix?.trim().takeIf { it?.isNotBlank() == true },
            title = book.title.trim(),
            titleSuffix = book.titleSuffix?.trim().takeIf { it?.isNotBlank() == true },
            coverUri = book.coverUri?.trim().takeIf { it?.isNotBlank() == true },
            description = book.description?.trim().takeIf { it?.isNotBlank() == true },
            publisher = book.publisher?.trim().takeIf { it?.isNotBlank() == true },
            language = book.language?.trim().takeIf { it?.isNotBlank() == true },
            pages = book.pages?.takeIf { it > 0 },
            format = book.format?.trim().takeIf { it?.isNotBlank() == true },
            purchaseFrom = book.purchaseFrom?.trim().takeIf { it?.isNotBlank() == true },
            purchasePrice = book.purchasePrice?.takeIf { it > 0 },
            purchaseDate = book.purchaseDate?.takeIf { it > 0 },
            readStatus = book.readStatus?.trim().takeIf { it?.isNotBlank() == true },
            readPages = book.readPages?.takeIf { it > 0 },
            startReadingDate = book.startReadingDate?.takeIf { it > 0 },
            finishedReadingDate = book.finishedReadingDate?.takeIf { it > 0 },
            series = book.series?.trim().takeIf { it?.isNotBlank() == true },
            volume = book.volume?.takeIf { it > 0 },
            lentTo = book.lentTo?.trim().takeIf { it?.isNotBlank() == true },
            lentDate = book.lentDate?.takeIf { it > 0 },
            lentReturned = book.lentReturned?.takeIf { it > 0 }
        )
    }

    suspend fun saveBook() {
        val cleanedBook = cleanBookData(state.value.book)
        mutableState.update {
            it.copy(book = cleanedBook)
        }
        if (isEditing && bookId != null) {
            updateExistingBook()
        } else {
            addNewBook()
        }
    }

    fun showFailedToSaveSnackbar() =
        screenModelScope.launch {
            SnackbarController.sendSnackbarEvent(
                event =
                    SnackbarEvent(
                        message = "Title cannot be empty"
                    )
            )
        }

    fun showSaveSuccessSnackbar() =
        screenModelScope.launch {
            SnackbarController.sendSnackbarEvent(
                event =
                    SnackbarEvent(
                        message = if (isEditing) "Book updated successfully" else "Book added successfully"
                    )
            )
        }

    private suspend fun addNewBook() {
        addBook.insertBook(state.value.book)
        val newBookId = addBook.getLastInsertedBookRowId()
        saveContributors(newBookId)
    }

    private suspend fun updateExistingBook() {
        addBook.updateBook(bookId!!, state.value.book)

        state.value.removedContributors.forEach { contributorName ->
            val contributorId = addBook.getContributorIdByName(contributorName)
            if (contributorId != null) {
                addBook.deleteBookContributorByBookIdAndContributorId(bookId, contributorId)
            }
        }

        val existingContributors = addBook.getContributorsByBookId(bookId)
        existingContributors.forEach { contributor ->
            val contributorId = addBook.getContributorIdByName(contributor.name)
            if (contributorId != null) {
                addBook.deleteBookContributorByBookIdAndContributorId(bookId, contributorId)
            }
        }

        saveContributors(bookId)
    }

    private suspend fun saveContributors(bookId: Long) {
        state.value.contributorsMap.forEach { (_, contributor) ->
            if (contributor.name.isBlank()) {
                Log.d("DatabaseError", "Contributor with empty name cannot be saved.")
                return@forEach
            }
            try {
                val contributorId =
                    addBook.getContributorIdByName(contributor.name)
                        ?: runCatching {
                            addBook.insertContributor(Contributors(id = 0, name = contributor.name))
                            addBook.getLastInsertedContributorRowId()
                        }.getOrElse { e ->
                            if (e is SQLiteConstraintException) {
                                Log.d("DatabaseError", "Duplicate contributor detected: ${contributor.name}")
                                addBook.getContributorIdByName(contributor.name)
                            } else {
                                throw e
                            }
                        } ?: throw SQLiteConstraintException("Failed to retrieve contributor ID for ${contributor.name}")

                addBook.insertBookContributor(
                    Books_Contributors_Map(id = 0, book_id = bookId, contributor_id = contributorId, role = contributor.role.value)
                )
            } catch (e: SQLiteConstraintException) {
                Log.d("DatabaseError", "Constraint failed for contributor ${contributor.name}: ${e.message}")
            }
        }
    }

    fun updateBook(update: Books.() -> Books) {
        mutableState.update {
            it.copy(book = it.book.update(), hasUnsavedChanges = true)
        }
    }

    fun addContributor(
        name: String,
        role: Roles
    ) {
        val nextId =
            state.value.contributorsMap.keys
                .maxOrNull()
                ?.plus(1) ?: 1
        mutableState.update { currentState ->
            val updatedMap =
                currentState.contributorsMap.toMutableMap().apply {
                    put(nextId, Contributor(name, role))
                }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }

    fun updateContributor(
        id: Int,
        name: String,
        role: Roles
    ) {
        mutableState.update { currentState ->
            val updatedMap =
                currentState.contributorsMap.toMutableMap().apply {
                    this[id] = Contributor(name, role)
                }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }

    fun removeContributor(id: Int) {
        val contributorToRemove = state.value.contributorsMap[id]
        contributorToRemove?.let { contributor ->
            mutableState.update { currentState ->
                val updatedMap =
                    currentState.contributorsMap.toMutableMap().apply {
                        remove(id)
                    }
                val updatedRemovedContributors =
                    currentState.removedContributors.toMutableSet().apply {
                        add(contributor.name)
                    }
                currentState.copy(
                    contributorsMap = updatedMap,
                    removedContributors = updatedRemovedContributors,
                    hasUnsavedChanges = true
                )
            }
        }
    }

    fun toggleDiscardDialog() {
        mutableState.update {
            it.copy(showDiscardDialog = !it.showDiscardDialog)
        }
    }

    fun toggleSaving() {
        mutableState.update {
            it.copy(isSaving = !it.isSaving)
        }
    }

    fun deleteImageFile(uri: Uri) {
        try {
            val file = uri.path?.let { File(it) }
            if (file != null && file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            Log.e("AddEditScreen", "Error deleting image file", e)
        }
    }
}