package com.frost23z.bookshelf.ui.addedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.ui.addedit.components.InfoSection
import com.frost23z.bookshelf.ui.addedit.components.PurchaseSection
import com.frost23z.bookshelf.ui.addedit.components.StatusSection
import com.frost23z.bookshelf.ui.addedit.components.TitleSection
import com.frost23z.bookshelf.ui.core.util.maxCutoutPadding
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.theme.padding
import kotlinx.coroutines.launch
import kotlin.text.toLongOrNull

object AddEditTab : Tab {
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 1u,
                title = "Add/Edit",
                icon = rememberVectorPainter(Icons.AutoMirrored.Filled.NoteAdd),
            )
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val screenModel = koinScreenModel<AddEditScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        val snackbarHostState = remember { SnackbarHostState() }

        DisposableEffect(Unit) {
            screenModel.reset()

            // Cleanup if needed when navigating away
            onDispose { }
        }

        Scaffold(
            topBar = {
                // TODO: TopBar
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(
                        horizontal = maxCutoutPadding(), vertical = MaterialTheme.padding.small
                    ), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TODO: Cover Section

                TitleSection(titlePrefix = state.titlePrefix ?: "",
                    onTitlePrefixChange = { screenModel.updateTitlePrefix(it) },
                    title = state.title,
                    onTitleChange = { screenModel.updateTitle(it) },
                    titleSuffix = state.titleSuffix ?: "",
                    onTitleSuffixChange = { screenModel.updateTitleSuffix(it) })

                InfoSection(publisher = state.publisher ?: "",
                    onPublisherChange = { screenModel.updatePublisher(it) },
                    language = state.language ?: "",
                    onLanguageChange = { screenModel.updateLanguage(it) },
                    pages = state.pages ?: "",
                    onPagesChange = { screenModel.updatePages(it) },
                    format = state.format ?: "",
                    onFormatChange = { screenModel.updateFormat(it) })

                PurchaseSection(purchaseFrom = state.purchaseFrom ?: "",
                    onPurchaseFromChange = { screenModel.updatePurchaseFrom(it) },
                    purchasePrice = state.purchasePrice ?: "",
                    onPurchasePriceChange = { screenModel.updatePurchasePrice(it) },
                    purchaseDate = state.purchaseDate ?: "",
                    onPurchaseDateChange = { screenModel.updatePurchaseDate(it) })

                StatusSection(totalPages = state.pages?.toLongOrNull() ?: 0,
                    readPages = state.readPages ?: 0,
                    onStatusChange = { screenModel.updateStatus(it) },
                    onReadPagesChange = { screenModel.updateReadPages(it) })

                Button(
                    onClick = {
                        scope.launch {
                            screenModel.addBook()
                            val snackbarResult = snackbarHostState.showSnackbar(
                                message = "Book saved",
                                withDismissAction = true
                            )
                            if (snackbarResult == SnackbarResult.Dismissed) {
                                tabNavigator.current = LibraryTab
                            }
                        }
                    }, modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Save")
                }
            }
        }
    }
}