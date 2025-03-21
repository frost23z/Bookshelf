package com.frost23z.bookshelf.ui.home

import com.frost23z.bookshelf.ui.core.navigation.Destination

sealed class HomeScreenEvent {
    data object ToggleBottomBarVisibility : HomeScreenEvent()

    data object ToggleAddOptionsBottomsheet : HomeScreenEvent()

    data class SwitchTab(val destination: Destination) : HomeScreenEvent()

    data object AddBook : HomeScreenEvent()
}