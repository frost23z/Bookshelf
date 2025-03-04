package com.frost23z.bookshelf.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.frost23z.bookshelf.ui.addedit.AddEditScreen
import com.frost23z.bookshelf.ui.lentborrowed.BorrowedScreen
import com.frost23z.bookshelf.ui.lentborrowed.LentScreen
import com.frost23z.bookshelf.ui.library.LibraryScreen
import com.frost23z.bookshelf.ui.more.MoreScreen
import com.frost23z.bookshelf.ui.reading.ReadingScreen

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
	navigation<NavBarDestinations.Library>(startDestination = LibraryScreen) {
		composable<LibraryScreen> {
			LibraryScreen()
		}
	}
}

fun NavGraphBuilder.readingGraph() {
	navigation<NavBarDestinations.Reading>(startDestination = ReadingScreen) {
		composable<ReadingScreen> {
			ReadingScreen()
		}
	}
}

fun NavGraphBuilder.addEditGraph() {
	navigation<NavBarDestinations.AddEdit>(startDestination = AddEditScreen) {
		composable<AddEditScreen> {
			AddEditScreen()
		}
	}
}

fun NavGraphBuilder.lentBorrowedGraph() {
	navigation<NavBarDestinations.LentBorrowed>(startDestination = LentScreen) {
		composable<LentScreen> {
			LentScreen()
		}
		composable<BorrowedScreen> {
			BorrowedScreen()
		}
	}
}

fun NavGraphBuilder.moreGraph() {
	navigation<NavBarDestinations.More>(startDestination = MoreScreen) {
		composable<MoreScreen> {
			MoreScreen()
		}
	}
}