package com.frost23z.bookshelf.ui.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationActions {
	data class Navigate(val destination: Destination, val navOptions: NavOptionsBuilder.() -> Unit = {}) : NavigationActions

	data object NavigateUp : NavigationActions
}