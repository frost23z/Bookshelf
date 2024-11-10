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
    private val isEditing: Boolean = false,
    private val bookId: Long? = null
) : StateScreenModel<AddEditScreenModel.State>(State()) {
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
                        hasUnsavedChanges = false
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
        saveContributors(bookId)
        deleteRemovedContributors(bookId)
    }

    private suspend fun saveContributors(bookId: Long) {
        state.value.contributorsMap.forEach { (_, contributor) ->
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

    private suspend fun deleteRemovedContributors(bookId: Long) {
        // Get the contributors currently associated with the book in the database
        val currentContributors = addBook.getContributorsByBookId(bookId)

        // Find the contributors that have been removed from the state
        val contributorsToRemove =
            currentContributors.filterNot { contributor ->
                state.value.contributorsMap.values
                    .any { it.name == contributor.name }
            }

        // Delete the removed contributors from the Books_Contributors_Map
        contributorsToRemove.forEach { contributor ->
            val contributorId = addBook.getContributorIdByName(contributor.name)
            if (contributorId != null) {
                addBook.deleteBookContributorByBookIdAndContributorId(bookId, contributorId)
            }
        }
    }

    suspend fun addBook() {
        addBook.insertBook(state.value.book)

        val bookId = addBook.getLastInsertedBookRowId()

        state.value.contributorsMap.forEach { (_, contributor) ->
            try {
                val contributorId =
                    addBook.getContributorIdByName(contributor.name)
                        ?: kotlin
                            .runCatching {
                                addBook.insertContributor(
                                    Contributors(
                                        id = 0,
                                        name = contributor.name
                                    )
                                )
                                addBook.getLastInsertedContributorRowId()
                            }.getOrElse { e ->
                                if (e is SQLiteConstraintException) {
                                    Log.d(
                                        "DatabaseError",
                                        "Duplicate contributor detected: ${contributor.name}"
                                    )
                                    addBook.getContributorIdByName(contributor.name)
                                } else {
                                    throw e
                                }
                            }
                        ?: throw SQLiteConstraintException("Failed to retrieve contributor ID for ${contributor.name}")

                addBook.insertBookContributor(
                    Books_Contributors_Map(
                        id = 0,
                        book_id = bookId,
                        contributor_id = contributorId,
                        role = contributor.role.value
                    )
                )
            } catch (e: SQLiteConstraintException) {
                Log.d(
                    "DatabaseError",
                    "Constraint failed for contributor ${contributor.name}: ${e.message}"
                )
            }
        }
    }

    data class State(
        val book: Books = books,
        val contributorsMap: MutableMap<Int, Contributor> =
            linkedMapOf(
                1 to
                    Contributor(
                        "",
                        Roles.AUTHOR
                    )
            ),
        val hasUnsavedChanges: Boolean = false
    )

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
            screenModelScope.launch {
                // Delete contributor from the database
                val contributorId = addBook.getContributorIdByName(contributor.name)
                if (contributorId != null) {
                    addBook.deleteBookContributorByBookIdAndContributorId(state.value.book.id, contributorId)
                }
            }
        }

        mutableState.update { currentState ->
            val updatedMap =
                currentState.contributorsMap.toMutableMap().apply {
                    remove(id)
                }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }
}