package com.frost23z.bookshelf.ui.library

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlipToBack
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

object LibraryTab : Tab {
    private fun readResolve(): Any = LibraryTab

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 0u,
                title = "Library",
                icon =
                    rememberAnimatedVectorPainter(
                        animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.anim_library_select),
                        atEnd = LocalTabNavigator.current.current.key == key
                    )
            )
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = koinScreenModel<LibraryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(topBar = {
            TopBar(
                title = "Library",
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
        }, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.padding(innerPadding))
                }

                state.library.isEmpty() -> {
                    EmptyScreen(modifier = Modifier.padding(innerPadding))
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
                                isSelected = isSelected, // Show selected state
                                onClick = {
                                    if (state.selectionCounter > 0) {
                                        screenModel.toggleSelection(book.id)
                                    } else {
                                        navigator.push(DetailsScreen(book))
                                    }
                                },
                                onLongClick = {
                                    screenModel.toggleSelection(book.id) // Start selection mode on long-click
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}