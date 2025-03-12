package com.frost23z.bookshelf.ui.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
	val startDestination: Destination
	val navigationActions: Flow<NavigationActions>

	suspend fun navigate(
		destination: Destination,
		navOptions: NavOptionsBuilder.() -> Unit = {}
	)

	suspend fun navigateUp()
}

class DefaultNavigator(
	override val startDestination: Destination
) : Navigator {
	private val _navigationActions = Channel<NavigationActions>(Channel.BUFFERED)
	override val navigationActions = _navigationActions.receiveAsFlow()

	override suspend fun navigate(
		destination: Destination,
		navOptions: NavOptionsBuilder.() -> Unit
	) {
		_navigationActions.send(NavigationActions.Navigate(destination, navOptions))
	}

	override suspend fun navigateUp() {
		_navigationActions.send(NavigationActions.NavigateUp)
	}
}