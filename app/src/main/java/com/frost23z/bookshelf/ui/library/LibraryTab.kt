package com.frost23z.bookshelf.ui.library

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.FlipToBack
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.components.ListItem
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.constants.MediumIcon
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import com.frost23z.bookshelf.ui.core.screen.LoadingScreen
import com.frost23z.bookshelf.ui.home.HomeScreen

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

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = koinScreenModel<LibraryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

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
        }) { innerPadding ->
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.padding(innerPadding))
                }

                state.filteredLibrary.isEmpty() -> {
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
                            ListItem(
                                headlineContent = book.title,
                                modifier =
                                    Modifier
                                        .background(
                                            color =
                                                if (isSelected) {
                                                    Color.LightGray
                                                } else {
                                                    Color.Transparent
                                                }
                                        ).fillMaxWidth()
                                        .combinedClickable(onClick = {
                                            if (state.selectionCounter >
                                                0
                                            ) {
                                                screenModel.toggleSelection(book.id)
                                            }
                                        }, onLongClick = { screenModel.toggleSelection(book.id) }),
                                leadingIcon = Icons.Outlined.AutoStories,
                                leadingIconDescription = "Book icon",
                                leadingImageUri = book.coverUri,
                                leadingImageDescription = "Book cover for ${book.title}",
                                iconModifier =
                                    Modifier.border(
                                        width = 1.dp,
                                        color = LocalContentColor.current,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                leadingIconSize = MediumIcon
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(state.selectionCounter) {
            HomeScreen.showBottomNavigation(state.selectionCounter == 0)
        }
    }
}