package com.frost23z.bookshelf.ui.home

import com.frost23z.bookshelf.ui.core.navigation.Destination

data class HomeScreenState(
    val currentTab: Int = Destination.navBarItems.indexOf(Destination.Library),
    val previousTab: Int = Destination.navBarItems.indexOf(Destination.Library),
    val isBottomBarVisible: Boolean = true,
    val isAddOptionsVisible: Boolean = false
)