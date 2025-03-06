package com.frost23z.bookshelf.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.frost23z.bookshelf.ui.core.components.Screen

class Navigator(val navController: NavHostController) {
	val current: NavDestination?
		@Composable
		get() = navController.currentBackStackEntryAsState().value?.destination

	private var bottomSheetScreen by mutableStateOf<Screen?>(null)

	fun push(screen: Screen) {
		navController.navigate(screen)
	}

	fun replace(screen: Screen) {
		navController.navigate(screen) {
			popUpTo(navController.graph.startDestinationId) { inclusive = true }
		}
	}

	fun pop() {
		navController.popBackStack()
	}

	fun popToRoot() {
		navController.popBackStack(navController.graph.startDestinationId, true)
	}

	fun switchTab(screen: Any) {
		navController.navigate(screen) {
			popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
			launchSingleTop = true
			restoreState = true
		}
	}

	@Composable
	fun isTabSelected(screen: Any): Boolean = current?.hierarchy?.any { it.hasRoute(screen::class) } == true

	fun showBottomSheet(screen: Screen) {
		bottomSheetScreen = screen
	}

	fun hideBottomSheet() {
		bottomSheetScreen = null
	}
}

val LocalNavigator = staticCompositionLocalOf<Navigator> {
	error("No AppNavigator provided!")
}

@Composable
fun rememberNavigator(): Navigator {
	val navController = rememberNavController()
	return remember { Navigator(navController) }
}