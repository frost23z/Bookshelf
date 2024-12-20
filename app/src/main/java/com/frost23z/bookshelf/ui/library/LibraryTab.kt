package com.frost23z.bookshelf.ui.library

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.outlined.FlipToBack
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import com.frost23z.bookshelf.ui.core.screen.LoadingScreen
import com.frost23z.bookshelf.ui.detail.DetailsScreen
import com.frost23z.bookshelf.ui.home.HomeScreen

object LibraryTab : Tab {
    private fun readResolve(): Any = LibraryTab

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 0u,
                title = stringResource(R.string.library),
                icon =
                    rememberAnimatedVectorPainter(
                        animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.anim_library),
                        atEnd = LocalTabNavigator.current.current.key == key
                    )
            )
        }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = koinScreenModel<LibraryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        Scaffold(topBar = {
            TopBar(
                title = stringResource(R.string.library),
                searchQuery = state.searchQuery,
                onSearchQueryChange = { screenModel.onSearchQueryChange(it) },
                selectionCounter = state.selectionCounter,
                onCancelSelection = { screenModel.onCancelSelection() },
                actions = {
                    // TODO: Actions
                },
                selectionActions = {
                    IconButton(
                        onClick = { screenModel.onClickSelectAll() },
                        icon = Icons.Outlined.SelectAll,
                        tooltip = "IncreaseSelectionCounter"
                    )
                    IconButton(
                        onClick = { screenModel.onClickInvertSelection() },
                        icon = Icons.Outlined.FlipToBack,
                        tooltip = "DecreaseSelectionCounter"
                    )
                }
            )
        }) { innerPadding ->
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.padding(innerPadding))
                }

                state.filteredLibrary.isEmpty() -> {
                    EmptyScreen(
                        icon = Icons.Default.LibraryBooks,
                        message = "No books found",
                        subtitle = "Add some books to your library to get started",
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(
                            items = state.filteredLibrary,
                            key = { it.id } // Use book's unique ID as key
                        ) { book ->
                            val isSelected = state.selectedBooks.contains(book.id)
                            LibraryBookItem(
                                book = book,
                                isSelected = isSelected,
                                onClick = {
                                    if (state.selectionCounter > 0) {
                                        screenModel.toggleSelection(book.id)
                                    } else {
                                        navigator.push(DetailsScreen(book.id))
                                    }
                                },
                                onLongClick = {
                                    screenModel.toggleSelection(book.id)
                                }
                            )
                        }
                    }
                }
            }

            LaunchedEffect(state.selectionCounter) {
                HomeScreen.showBottomNavigation(state.selectionCounter == 0)
            }
        }
    }
}