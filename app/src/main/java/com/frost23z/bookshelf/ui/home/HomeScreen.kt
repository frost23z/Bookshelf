package com.frost23z.bookshelf.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.frost23z.bookshelf.ui.addedit.AddEditScreen
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.NavigationBarItemScreen
import com.frost23z.bookshelf.ui.core.components.Screen
import com.frost23z.bookshelf.ui.core.navigation.AppNavHost
import com.frost23z.bookshelf.ui.core.navigation.NavigationBarDestinations
import com.frost23z.bookshelf.ui.lentborrowed.BorrowedScreen
import com.frost23z.bookshelf.ui.lentborrowed.LentScreen
import com.frost23z.bookshelf.ui.library.LibraryScreen
import com.frost23z.bookshelf.ui.more.MoreScreen
import com.frost23z.bookshelf.ui.reading.ReadingScreen

object HomeScreen : Screen {
	@SuppressLint("UnusedContentLambdaTargetStateParameter")
	@Composable
	override fun Content() {
		val navController = rememberNavController()
		val entry by navController.currentBackStackEntryAsState()
		val currentDestination = entry?.destination

		var currentIndex by remember { mutableIntStateOf(0) }
		var previousIndex by remember { mutableIntStateOf(0) }

		var showSecondaryScreen by remember { mutableStateOf(false) }

		Scaffold(
			bottomBar = {
				NavigationBar {
					NavigationBarDestinations.navItems.forEachIndexed { index, destination ->
						NavigationBarItem(
							screen = getCurrentScreen(destination, showSecondaryScreen),
							selected = currentDestination?.hierarchy?.any {
								it.hasRoute(destination::class)
							} == true,
							onClick = {
								previousIndex = currentIndex
								currentIndex = index
								navigateToDestination(navController, destination)
								showSecondaryScreen = false
							},
							onDoubleClick = {
								previousIndex = currentIndex
								currentIndex = index
								navigateToDestination(
									navController,
									destination,
									true,
									showSecondaryScreen
								)
								showSecondaryScreen =
									destination == NavigationBarDestinations.LentBorrowed &&
									!showSecondaryScreen
							}
						)
					}
				}
			},
			contentWindowInsets = WindowInsets(0)
		) { innerPadding ->
			AnimatedContent(
				targetState = currentDestination,
				transitionSpec = {
					when {
						currentIndex == previousIndex -> fadeIn() togetherWith fadeOut()
						currentIndex > previousIndex -> slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
						else -> slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
					}
				}
			) {
				AppNavHost(
					navController = navController,
					modifier = Modifier.padding(innerPadding)
				)
			}
		}
	}

	private fun navigateToDestination(
		navController: NavController,
		destination: Any,
		isDoubleClick: Boolean = false,
		showSecondaryScreen: Boolean = false
	) {
		val targetScreen =
			if (isDoubleClick && destination == NavigationBarDestinations.LentBorrowed) {
				if (showSecondaryScreen) LentScreen else BorrowedScreen
			} else {
				destination
			}
		navController.navigate(targetScreen) {
			popUpTo(navController.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}
	}

	private fun getCurrentScreen(
		destination: Any,
		showSecondaryScreen: Boolean = false
	): NavigationBarItemScreen = when (destination) {
		is NavigationBarDestinations.Library -> LibraryScreen
		is NavigationBarDestinations.Reading -> ReadingScreen
		is NavigationBarDestinations.AddEdit -> AddEditScreen
		is NavigationBarDestinations.LentBorrowed -> if (showSecondaryScreen) BorrowedScreen else LentScreen
		is NavigationBarDestinations.More -> MoreScreen
		else -> LibraryScreen
	}

	@Composable
	private fun RowScope.NavigationBarItem(
		screen: NavigationBarItemScreen,
		selected: Boolean,
		onClick: () -> Unit,
		onDoubleClick: (() -> Unit)? = null
	) {
		NavigationBarItem(
			selected = selected,
			onClick = {
				if (selected) {
					onDoubleClick?.invoke()
				} else {
					onClick()
				}
			},
			icon = {
				NavigationBarIcon(
					icon = screen.navigationItemData.icon,
					isSelected = selected
				)
			},
			label = {
				Text(text = screen.navigationItemData.label)
			}
		)
	}

	@OptIn(ExperimentalAnimationGraphicsApi::class)
	@Composable
	private fun NavigationBarIcon(
		icon: Icon,
		isSelected: Boolean
	) {
		when (icon) {
			is Icon.Static -> {
				Icon(icon = if (isSelected) icon.selectedIcon else icon.icon)
			}

			is Icon.Animated -> {
				val painter = rememberAnimatedVectorPainter(
					animatedImageVector = icon.animatedIcon,
					atEnd = isSelected
				)
				Icon(painter = painter, contentDescription = null)
			}
		}
	}
}