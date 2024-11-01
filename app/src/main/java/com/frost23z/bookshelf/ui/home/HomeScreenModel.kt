package com.frost23z.bookshelf.ui.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import com.frost23z.bookshelf.ui.library.LibraryTab
import kotlinx.coroutines.flow.update

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State()) {
    data class State(
        val showBottomNavigation: Boolean = true,
        val showLibraryBottomsheet: Boolean = false,
        val showAddOptionsBottomsheet: Boolean = false,
        val previousTab: Tab? = null,
        val currentTab: Tab = LibraryTab
    )

    fun toggleBottomNavigation() {
        mutableState.update { state -> state.copy(showBottomNavigation = !state.showBottomNavigation) }
    }

    fun toggleLibraryBottomsheet() {
        mutableState.update { state -> state.copy(showLibraryBottomsheet = !state.showLibraryBottomsheet) }
    }

    fun toggleAddOptionsBottomsheet() {
        mutableState.update { state -> state.copy(showAddOptionsBottomsheet = !state.showAddOptionsBottomsheet) }
    }

    fun setCurrentTab(tab: Tab) {
        mutableState.update { state -> state.copy(currentTab = tab) }
    }

    fun setPreviousTab(tab: Tab) {
        mutableState.update { state -> state.copy(previousTab = tab) }
    }
}
