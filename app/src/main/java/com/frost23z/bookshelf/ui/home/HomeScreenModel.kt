package com.frost23z.bookshelf.ui.home

import androidx.lifecycle.viewModelScope
import com.frost23z.bookshelf.ui.core.components.ScreenModel
import com.frost23z.bookshelf.ui.core.navigation.Destination
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel(
	private val navigator: Navigator
) : ScreenModel<HomeScreenState>(HomeScreenState()) {
	fun switchTab(destination: Destination) {
		viewModelScope.launch {
			navigator.navigate(destination) {
				popUpTo(Destination.Library) {
					inclusive = false
					saveState = true
				}
				launchSingleTop = true
				restoreState = true
			}
			mutableState.update {
				it.copy(
					previousTab = it.currentTab,
					currentTab = Destination.navBarItems.indexOf(destination)
				)
			}
		}
	}

	fun showAddOptions() {
		viewModelScope.launch {
			navigator.navigate(Destination.AddEdit) {
				popUpTo(Destination.AddEdit) {
					saveState = true
				}
				launchSingleTop = true
				restoreState = true
			}
		}
	}

	private fun updateState(update: HomeScreenState.() -> HomeScreenState) {
		mutableState.update { it.update() }
	}

	private fun gotoAddBook() {
		viewModelScope.launch {
			navigator.navigate(Destination.AddEdit) {
				launchSingleTop = true
				restoreState = true
			}
		}
	}

	fun onEvent(event: HomeScreenEvent) {
		when (event) {
			HomeScreenEvent.ToggleAddOptionsBottomsheet -> updateState { copy(isAddOptionsVisible = !isAddOptionsVisible) }
			HomeScreenEvent.ToggleBottomBarVisibility -> updateState { copy(isBottomBarVisible = !isBottomBarVisible) }
			HomeScreenEvent.AddBook -> gotoAddBook()
			is HomeScreenEvent.SwitchTab -> TODO()
		}
	}
}