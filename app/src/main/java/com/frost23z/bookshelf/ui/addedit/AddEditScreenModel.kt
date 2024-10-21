package com.frost23z.bookshelf.ui.addedit

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

class AddEditScreenModel : StateScreenModel<AddEditScreenModel.State>(State()) {
    data class State(
        var id: Long = 0,
        var favorite: Boolean = false,
        var dateAdded: Long = 0,
        var titlePrefix: String? = null,
        var title: String = "",
        var titleSuffix: String? = null,
        var coverUrl: String? = null,
        var summary: String? = null,
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
    )

    fun updateFavorite(favorite: Boolean) {
        mutableState.update { it.copy(favorite = favorite) }
    }

    fun updateTitlePrefix(titlePrefix: String) {
        mutableState.update { it.copy(titlePrefix = titlePrefix) }
    }

    fun updateTitle(title: String) {
        mutableState.update { it.copy(title = title) }
    }

    fun updateTitleSuffix(titleSuffix: String) {
        mutableState.update { it.copy(titleSuffix = titleSuffix) }
    }

    fun updateCoverUrl(coverUrl: String) {
        mutableState.update { it.copy(coverUrl = coverUrl) }
    }

    fun updateSummary(summary: String) {
        mutableState.update { it.copy(summary = summary) }
    }

    fun updatePublisher(publisher: String) {
        mutableState.update { it.copy(publisher = publisher) }
    }

    fun updateLanguage(language: String) {
        mutableState.update { it.copy(language = language) }
    }

    fun updatePages(pages: String) {
        mutableState.update { it.copy(pages = pages) }
    }

    fun updateFormat(format: String) {
        mutableState.update { it.copy(format = format) }
    }

    fun updatePurchaseFrom(purchaseFrom: String) {
        mutableState.update { it.copy(purchaseFrom = purchaseFrom) }
    }

    fun updatePurchasePrice(purchasePrice: String) {
        mutableState.update { it.copy(purchasePrice = purchasePrice) }
    }

    fun updatePurchaseDate(purchaseDate: String) {
        mutableState.update { it.copy(purchaseDate = purchaseDate) }
    }

    fun updateStatus(status: String) {
        mutableState.update { it.copy(status = status) }
    }

    fun updateReadPages(readPages: Long) {
        mutableState.update { it.copy(readPages = readPages) }
    }

    fun updateSeries(series: String) {
        mutableState.update { it.copy(series = series) }
    }

    fun updateVolume(volume: Long) {
        mutableState.update { it.copy(volume = volume) }
    }
}