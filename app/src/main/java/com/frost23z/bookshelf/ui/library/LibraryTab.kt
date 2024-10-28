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
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import com.frost23z.bookshelf.ui.core.screen.LoadingScreen
import com.frost23z.bookshelf.ui.detail.DetailsScreen

object LibraryTab : Tab {
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

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = koinScreenModel<LibraryScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(topBar = {
            // TODO: TopBar
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
                        items(state.library) { book ->
                            LibraryBookItem(book = book, onClick = {
                                navigator.push(DetailsScreen(book))
                            })
                        }
                    }
                }
            }
        }
    }
}