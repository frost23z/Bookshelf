package com.frost23z.bookshelf.ui.home

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State()) {
    fun toggleBottomNavigation() {
        mutableState.update { state -> state.copy(showBottomNavigation = !state.showBottomNavigation) }
    }

    data class State(
        var showBottomNavigation: Boolean = true
    )
}
