package com.frost23z.bookshelf.ui.library

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object LibraryTab : Tab {
    override val options: TabOptions
        @Composable get() {
            LocalTabNavigator.current.current.key == key
            return TabOptions(
                index = 0u,
                title = "Library",
                icon = rememberVectorPainter(Icons.AutoMirrored.Filled.LibraryBooks),
            )
        }

    @Composable
    override fun Content() {

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            topBar = {
                // TODO: TopBar
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            // TODO: Content
        }
    }
}