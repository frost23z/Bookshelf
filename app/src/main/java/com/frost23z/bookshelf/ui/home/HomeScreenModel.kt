package com.frost23z.bookshelf.ui.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import com.frost23z.bookshelf.ui.library.LibraryTab
import kotlinx.coroutines.flow.update

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State()) {
    data class State(
        val showLibraryBottomsheet: Boolean = false,
        val showAddOptionsBottomsheet: Boolean = false,
        val previousTab: Tab? = null,
        val currentTab: Tab = LibraryTab
    )

    private fun safeStateUpdate(update: (State) -> State) {
        try {
            mutableState.update(update)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleLibraryBottomsheet() = safeStateUpdate { it.copy(showLibraryBottomsheet = !it.showLibraryBottomsheet) }

    fun toggleAddOptionsBottomsheet() = safeStateUpdate { it.copy(showAddOptionsBottomsheet = !it.showAddOptionsBottomsheet) }

    fun setCurrentTab(tab: Tab) = safeStateUpdate { it.copy(currentTab = tab, previousTab = it.currentTab) }

    fun setPreviousTab(tab: Tab) = mutableState.update { state -> state.copy(previousTab = tab) }
}
