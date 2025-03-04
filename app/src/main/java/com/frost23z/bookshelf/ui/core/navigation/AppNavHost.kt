package com.frost23z.bookshelf.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.frost23z.bookshelf.ui.addedit.AddEditTab
import com.frost23z.bookshelf.ui.lentborrowed.BorrowedScreen
import com.frost23z.bookshelf.ui.lentborrowed.LentTab
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.more.MoreTab
import com.frost23z.bookshelf.ui.reading.ReadingTab

@Composable
fun AppNavHost(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = NavBarDestinations.Library,
		modifier = modifier
	) {
		libraryGraph()
		readingGraph()
		addEditGraph()
		lentBorrowedGraph()
		moreGraph()
	}
}

fun NavGraphBuilder.libraryGraph() {
	navigation<NavBarDestinations.Library>(startDestination = LibraryTab) {
		composable<LibraryTab> {
			LibraryTab()
		}
	}
}

fun NavGraphBuilder.readingGraph() {
	navigation<NavBarDestinations.Reading>(startDestination = ReadingTab) {
		composable<ReadingTab> {
			ReadingTab()
		}
	}
}

fun NavGraphBuilder.addEditGraph() {
	navigation<NavBarDestinations.AddEdit>(startDestination = AddEditTab) {
		composable<AddEditTab> {
			AddEditTab()
		}
	}
}

fun NavGraphBuilder.lentBorrowedGraph() {
	navigation<NavBarDestinations.LentBorrowed>(startDestination = LentTab) {
		composable<LentTab> {
			LentTab()
		}
		composable<BorrowedScreen> {
			BorrowedScreen()
		}
	}
}

fun NavGraphBuilder.moreGraph() {
	navigation<NavBarDestinations.More>(startDestination = MoreTab) {
		composable<MoreTab> {
			MoreTab()
		}
	}
}