package com.frost23z.bookshelf.ui.addedit

import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
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
        addBook.insertBook(
            Books(
                id = state.value.id,
                favorite = state.value.favorite,
                dateAdded = state.value.dateAdded,
                titlePrefix = state.value.titlePrefix,
                title = state.value.title,
                titleSuffix = state.value.titleSuffix,
                coverUri = state.value.coverUri.toString(),
                description = state.value.description,
                publisher = state.value.publisher,
                language = state.value.language,
                pages = state.value.pages?.toLongOrNull(),
                format = state.value.format,
                purchaseFrom = state.value.purchaseFrom,
                purchasePrice = state.value.purchasePrice?.toLongOrNull(),
                purchaseDate = state.value.purchaseDate?.toLongOrNull(),
                status = state.value.status,
                readPages = state.value.readPages,
                series = state.value.series,
                volume = state.value.volume,
            )
        )

        val bookId = addBook.getLastInsertedBookRowId()

        state.value.contributorsMap.forEach { (_, contributor) ->
            try {
                val contributorId = addBook.getContributorIdByName(contributor.name)
                    ?: kotlin.runCatching {
                        addBook.insertContributor(Contributors(id = 0, name = contributor.name))
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

    fun reset() {
        mutableState.update { State() }
    }

    data class State(
        var id: Long = 0,
        var favorite: Boolean = false,
        var dateAdded: Long = 0,
        var titlePrefix: String? = null,
        var title: String = "",
        var titleSuffix: String? = null,
        var coverUri: Uri? = null,
        var description: String? = null,
        var publisher: String? = null,
        var language: String? = null,
        var pages: String? = null,
        var format: String? = null,
        var purchaseFrom: String? = null,
        var purchasePrice: String? = null,
        var purchaseDate: String? = null,
        var status: String? = null,
        var readPages: Long? = null,
        var series: String? = null,
        var volume: Long? = null,

        var contributorsMap: MutableMap<Int, Contributor> = linkedMapOf(
            1 to Contributor(
                "",
                Roles.AUTHOR
            )
        ),

        var hasUnsavedChanges: Boolean = false
    )

    fun updateFavorite(favorite: Boolean) {
        mutableState.update { it.copy(favorite = favorite, hasUnsavedChanges = true) }
    }

    fun updateTitlePrefix(titlePrefix: String) {
        mutableState.update { it.copy(titlePrefix = titlePrefix, hasUnsavedChanges = true) }
    }

    fun updateTitle(title: String) {
        mutableState.update { it.copy(title = title, hasUnsavedChanges = true) }
    }

    fun updateTitleSuffix(titleSuffix: String) {
        mutableState.update { it.copy(titleSuffix = titleSuffix, hasUnsavedChanges = true) }
    }

    fun updateCoverUri(coverUri: Uri?) {
        mutableState.update { it.copy(coverUri = coverUri, hasUnsavedChanges = true) }
    }

    fun updateDescription(description: String) {
        mutableState.update { it.copy(description = description, hasUnsavedChanges = true) }
    }

    fun updatePublisher(publisher: String) {
        mutableState.update { it.copy(publisher = publisher, hasUnsavedChanges = true) }
    }

    fun updateLanguage(language: String) {
        mutableState.update { it.copy(language = language, hasUnsavedChanges = true) }
    }

    fun updatePages(pages: String) {
        mutableState.update { it.copy(pages = pages, hasUnsavedChanges = true) }
    }

    fun updateFormat(format: String) {
        mutableState.update { it.copy(format = format, hasUnsavedChanges = true) }
    }

    fun updatePurchaseFrom(purchaseFrom: String) {
        mutableState.update { it.copy(purchaseFrom = purchaseFrom, hasUnsavedChanges = true) }
    }

    fun updatePurchasePrice(purchasePrice: String) {
        mutableState.update { it.copy(purchasePrice = purchasePrice, hasUnsavedChanges = true) }
    }

    fun updatePurchaseDate(purchaseDate: String) {
        mutableState.update { it.copy(purchaseDate = purchaseDate, hasUnsavedChanges = true) }
    }

    fun updateStatus(status: String) {
        mutableState.update { it.copy(status = status, hasUnsavedChanges = true) }
    }

    fun updateReadPages(readPages: Long) {
        mutableState.update { it.copy(readPages = readPages, hasUnsavedChanges = true) }
    }

    fun updateSeries(series: String) {
        mutableState.update { it.copy(series = series, hasUnsavedChanges = true) }
    }

    fun updateVolume(volume: Long) {
        mutableState.update { it.copy(volume = volume, hasUnsavedChanges = true) }
    }

    fun addContributor(name: String, role: Roles) {
        val nextId = state.value.contributorsMap.keys.maxOrNull()?.plus(1) ?: 1
        mutableState.update { currentState ->
            val updatedMap = currentState.contributorsMap.toMutableMap().apply {
                put(nextId, Contributor(name, role))
            }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }

    fun updateContributor(id: Int, name: String, role: Roles) {
        mutableState.update { currentState ->
            val updatedMap = currentState.contributorsMap.toMutableMap().apply {
                this[id] = Contributor(name, role)
            }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }

    fun removeContributor(id: Int) {
        mutableState.update { currentState ->
            val updatedMap = currentState.contributorsMap.toMutableMap().apply {
                remove(id)
            }
            currentState.copy(contributorsMap = updatedMap, hasUnsavedChanges = true)
        }
    }
}