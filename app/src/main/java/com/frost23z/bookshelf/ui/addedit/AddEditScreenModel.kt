package com.frost23z.bookshelf.ui.addedit

import android.database.sqlite.SQLiteConstraintException
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        val showDiscardDialog: Boolean = false
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
                        showDiscardDialog = false
                    )
                }
            }
        }
    }

    suspend fun saveBook() {
        if (isEditing && bookId != null) {
            updateExistingBook()
        } else {
            addNewBook()
        }
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
}