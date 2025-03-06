package com.frost23z.bookshelf.ui.home

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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.frost23z.bookshelf.ui.addedit.AddEditTab
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.Screen
import com.frost23z.bookshelf.ui.core.components.Tab
import com.frost23z.bookshelf.ui.core.navigation.AppNavHost
import com.frost23z.bookshelf.ui.core.navigation.LocalNavigator
import com.frost23z.bookshelf.ui.core.navigation.NavBarDestinations
import com.frost23z.bookshelf.ui.core.navigation.rememberNavigator
import com.frost23z.bookshelf.ui.lentborrowed.BorrowedScreen
import com.frost23z.bookshelf.ui.lentborrowed.LentTab
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.more.MoreTab
import com.frost23z.bookshelf.ui.reading.ReadingTab

object HomeScreen : Screen {
	@Composable
	override fun Content() {
		val navigator = rememberNavigator()

		var currentIndex by remember { mutableIntStateOf(0) }
		var previousIndex by remember { mutableIntStateOf(0) }

		CompositionLocalProvider(LocalNavigator provides navigator) {
			Scaffold(
				bottomBar = {
					NavigationBar {
						NavBarDestinations.navBarItems.forEachIndexed { index, destination ->
							NavigationBarItem(
								screen = getCurrentScreen(destination),
								selected = LocalNavigator.current.isTabSelected(destination),
								onClick = {
									navigator.switchTab(destination)
									previousIndex = currentIndex
									currentIndex = index
								}
							)
						}
					}
				},
				contentWindowInsets = WindowInsets(0)
			) { innerPadding ->
				AnimatedContent(
					targetState = LocalNavigator.current.current,
					transitionSpec = {
						when {
							currentIndex == previousIndex -> fadeIn() togetherWith fadeOut()
							currentIndex > previousIndex -> slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
							else -> slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
						}
					}
				) { _ ->
					AppNavHost(
						navController = navigator.navController,
						modifier = Modifier.padding(innerPadding)
					)
				}
			}
		}
	}

	private fun getCurrentScreen(
		destination: Any,
		showSecondaryScreen: Boolean = false
	): Tab = when (destination) {
		is NavBarDestinations.Library -> LibraryTab
		is NavBarDestinations.Reading -> ReadingTab
		is NavBarDestinations.AddEdit -> AddEditTab
		is NavBarDestinations.LentBorrowed -> if (showSecondaryScreen) BorrowedScreen else LentTab
		is NavBarDestinations.More -> MoreTab
		else -> LibraryTab
	}

	@Composable
	private fun RowScope.NavigationBarItem(
		screen: Tab,
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