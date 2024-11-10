package com.frost23z.bookshelf.ui.addedit

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import cafe.adriel.voyager.core.model.StateScreenModel
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.data.Books_Contributors_Map
import com.frost23z.bookshelf.data.Contributors
import com.frost23z.bookshelf.data.Roles
import com.frost23z.bookshelf.domain.interactor.AddBook
import com.frost23z.bookshelf.ui.addedit.components.Contributor
import kotlinx.coroutines.flow.update

class AddEditScreenModel(
    private val addBook: AddBook
) : StateScreenModel<AddEditScreenModel.State>(State()) {
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
        val book: Books =
            Books(
                id = 0,
                favorite = false,
                dateAdded = 0,
                dateLastUpdated = 0,
                titlePrefix = null,
                title = "",
                titleSuffix = null,
                coverUri = null,
                description = null,
                publisher = null,
                language = null,
                pages = null,
                format = null,
                purchaseFrom = null,
                purchasePrice = null,
                purchaseDate = null,
                readStatus = null,
                readPages = null,
                startReadingDate = null,
                finishedReadingDate = null,
                series = null,
                volume = null,
                isLent = false,
                lentTo = null,
                lentDate = null,
                lentReturned = null
            ),
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
        mutableState.update { currentState ->
            val updatedMap =
                currentState.contributorsMap.toMutableMap().apply {
                    remove(id)
                }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }
}