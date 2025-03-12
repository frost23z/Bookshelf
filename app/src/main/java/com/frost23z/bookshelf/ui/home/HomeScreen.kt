package com.frost23z.bookshelf.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.addedit.AddEditTab
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.Screen
import com.frost23z.bookshelf.ui.core.components.Tab
import com.frost23z.bookshelf.ui.core.navigation.AppNavHost
import com.frost23z.bookshelf.ui.core.navigation.Destination
import com.frost23z.bookshelf.ui.core.navigation.NavigationActions
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import com.frost23z.bookshelf.ui.lentborrowed.BorrowedScreen
import com.frost23z.bookshelf.ui.lentborrowed.LentTab
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.more.MoreTab
import com.frost23z.bookshelf.ui.reading.ReadingTab
import com.frost23z.bookshelf.utility.ObserveAsEvents
import com.frost23z.bookshelf.utility.SnackbarEventObserver
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

object HomeScreen : Screen {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	@Composable
	override fun Content() {
		val navController = rememberNavController()

		val navigator = koinInject<Navigator>()

		ObserveAsEvents(flow = navigator.navigationActions) { action ->
			when (action) {
				is NavigationActions.Navigate -> {
					navController.navigate(action.destination) {
						action.navOptions(this)
					}
				}
				NavigationActions.NavigateUp -> navController.navigateUp()
			}
		}

		val snackbarHostState = remember { SnackbarHostState() }
		SnackbarEventObserver(snackbarHostState = snackbarHostState)

		val screenModel = koinViewModel<HomeScreenModel>()
		val state by screenModel.state.collectAsStateWithLifecycle()

		Scaffold(
			bottomBar = {
				AnimatedVisibility(
					visible = state.isBottomBarVisible,
					enter = expandHorizontally(),
					exit = shrinkVertically()
				) {
					NavigationBar {
						Destination.navBarItems.forEachIndexed { index, destination ->
							NavigationBarItem(
								screen = getCurrentScreen(destination),
								selected = navController
									.currentBackStackEntryAsState()
									.value
									?.destination
									?.hierarchy
									?.any { it.hasRoute(destination::class) } == true,
								onClick = {
									screenModel.switchTab(destination)
									Log.d("HomeScreen", "onClick: $destination ${state.previousTab} ${state.currentTab}")
								}
							)
							if (index == (Destination.navBarItems.size / 2) - 1) {
								// Spacer(modifier = Modifier.weight(1f))
								NavigationBarMiddleItem(modifier = Modifier.weight(1f))
							}
						}
					}
				}
			},
			snackbarHost = {
				SnackbarHost(
					hostState = snackbarHostState,
					modifier = Modifier.padding(bottom = 72.dp)
				)
			},
			floatingActionButton = {
				AnimatedVisibility(
					visible = state.isBottomBarVisible,
					enter = expandHorizontally(),
					exit = shrinkVertically()
				) {
					FloatingActionButton(
						onClick = { screenModel.onEvent(HomeScreenEvent.ToggleAddOptionsBottomsheet) },
						containerColor = MaterialTheme.colorScheme.primary,
						shape = CircleShape,
						modifier = Modifier.offset(y = 44.dp),
					) {
						Icon(
							rememberAnimatedVectorPainter(
								animatedImageVector =
									AnimatedImageVector.animatedVectorResource(R.drawable.anim_add),
								atEnd = state.isBottomBarVisible
							),
							contentDescription = "Add"
						)
					}
				}
			},
			floatingActionButtonPosition = FabPosition.Center,
			contentWindowInsets = WindowInsets(0)
		) { innerPadding ->
			AnimatedContent(
				targetState = state.currentTab,
				transitionSpec = {
					when {
						state.currentTab == state.previousTab -> fadeIn() togetherWith fadeOut()
						state.currentTab > state.previousTab -> slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
						else -> slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
					}
				}
			) { _ ->
				AppNavHost(
					navController = navController,
					modifier = Modifier.padding(innerPadding)
				)
			}
		}
	}

	private fun getCurrentScreen(
		destination: Any,
		showSecondaryScreen: Boolean = false
	): Tab = when (destination) {
		is Destination.Library -> LibraryTab
		is Destination.Reading -> ReadingTab
		is Destination.AddEdit -> AddEditTab
		is Destination.Lent -> if (showSecondaryScreen) BorrowedScreen else LentTab
		is Destination.More -> MoreTab
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

	@Composable
	private fun NavigationBarMiddleItem(modifier: Modifier = Modifier) {
		Box(
			modifier = modifier.size(80.dp), // Standard NavigationBar Container height
			contentAlignment = Alignment.Center
		) {
			Box(
				modifier = Modifier.padding(top = 32.dp), // 12dp + 16dp + 4dp = 32dp from top
				contentAlignment = Alignment.Center
			) {
				Text(
					text = stringResource(R.string.add),
					style = MaterialTheme.typography.labelMedium,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
	}
}