package com.frost23z.bookshelf.ui.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.frost23z.bookshelf.ui.addedit.AddEditTab
import com.frost23z.bookshelf.ui.lentborrowed.LentTab
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.more.MoreTab
import com.frost23z.bookshelf.ui.reading.ReadingTab
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = koinInject<Navigator>().startDestination,
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
	navigation<Destination.LibraryGraph>(startDestination = Destination.Library) {
		composable<Destination.Library> {
			LibraryTab()
		}
		composable<Destination.Detail> {
			// DetailScreen()
		}
	}
}

fun NavGraphBuilder.readingGraph() {
	navigation<Destination.ReadingGraph>(startDestination = Destination.Reading) {
		composable<Destination.Reading> {
			ReadingTab()
		}
	}
}

fun NavGraphBuilder.addEditGraph() {
	navigation<Destination.AddEditGraph>(startDestination = Destination.AddEdit) {
		composable<Destination.AddEdit> {
			AddEditTab()
		}
	}
}

fun NavGraphBuilder.lentBorrowedGraph() {
	navigation<Destination.LentGraph>(startDestination = Destination.Lent) {
		composable<Destination.Lent> {
			LentTab()
		}
	}
}

fun NavGraphBuilder.moreGraph() {
	navigation<Destination.MoreGraph>(startDestination = Destination.More) {
		composable<Destination.More> {
			MoreTab()
		}
	}
}