package com.frost23z.bookshelf.ui.addedit.components.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object AddEditTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 1u,
                title = "Add/Edit",
                icon = rememberVectorPainter(Icons.AutoMirrored.Filled.NoteAdd),
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