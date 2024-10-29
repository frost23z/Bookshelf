package com.frost23z.bookshelf.ui.addedit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.ui.library.LibraryTab

object AddEditTab : Tab {
    private fun readResolve(): Any = AddEditTab

    var previousTab: Tab? = null

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 1u,
                title = "Add",
                icon = rememberVectorPainter(Icons.AutoMirrored.Filled.NoteAdd),
            )
        }

    @Composable
    override fun Content() {
        previousTab?.Content() ?: LibraryTab.Content()
    }
}