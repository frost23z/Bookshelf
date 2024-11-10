package com.frost23z.bookshelf.ui.addedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.frost23z.bookshelf.data.Roles
import com.frost23z.bookshelf.ui.addedit.components.ContributorsSection
import com.frost23z.bookshelf.ui.addedit.components.CoverSection
import com.frost23z.bookshelf.ui.addedit.components.InfoSection
import com.frost23z.bookshelf.ui.addedit.components.PurchaseSection
import com.frost23z.bookshelf.ui.addedit.components.StatusSection
import com.frost23z.bookshelf.ui.addedit.components.TitleSection
import com.frost23z.bookshelf.ui.addedit.components.camera.clearTempImageCache
import com.frost23z.bookshelf.ui.addedit.components.camera.moveImageToCoverFolder
import com.frost23z.bookshelf.ui.core.constants.MediumPadding
import com.frost23z.bookshelf.ui.core.constants.SmallPadding
import com.frost23z.bookshelf.ui.core.util.maxCutoutPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

data class AddEditScreen(
    private val isEditing: Boolean = false,
    private val bookId: Long? = null
) : Screen {
    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AddEditScreenModel> { parametersOf(isEditing, bookId) }
        val state by screenModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val navigator = LocalNavigator.currentOrThrow

        val snackbarHostState = remember { SnackbarHostState() }

        var isSaving = rememberSaveable { mutableStateOf(false) }

        Scaffold(
            topBar = {
                // TODO: TopBar
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .padding(
                            horizontal = maxOf(maxCutoutPadding(), MediumPadding),
                            vertical = SmallPadding
                        ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MediumPadding)
            ) {
                CoverSection(
                    coverUri = state.book.coverUri?.toUri(),
                    onCoverUriChange = { screenModel.updateBook { copy(coverUri = it.toString()) } },
                    navigator = navigator
                )

                TitleSection(
                    titlePrefix = state.book.titlePrefix ?: "",
                    onTitlePrefixChange = { screenModel.updateBook { copy(titlePrefix = it) } },
                    title = state.book.title,
                    onTitleChange = { screenModel.updateBook { copy(title = it) } },
                    titleSuffix = state.book.titleSuffix ?: "",
                    onTitleSuffixChange = { screenModel.updateBook { copy(titleSuffix = it) } }
                )

                ContributorsSection(
                    contributors = state.contributorsMap,
                    onContributorNameChange = { id, name ->
                        screenModel.updateContributor(
                            id,
                            name,
                            state.contributorsMap[id]?.role ?: Roles.AUTHOR
                        )
                    },
                    onContributorRoleChange = { id, role ->
                        screenModel.updateContributor(
                            id,
                            state.contributorsMap[id]?.name ?: "",
                            role
                        )
                        // Log.d("AddEditScreen", state.contributorsMap.toString())
                    },
                    onAddContributor = {
                        screenModel.addContributor("", Roles.AUTHOR)
                        // Log.d("AddEditScreen", state.contributorsMap.toString())
                    },
                    onRemoveContributor = { id ->
                        screenModel.removeContributor(id)
                        // Log.d("AddEditScreen", state.contributorsMap.toString())
                    }
                )

                InfoSection(
                    publisher = state.book.publisher ?: "",
                    onPublisherChange = { screenModel.updateBook { copy(publisher = it) } },
                    language = state.book.language ?: "",
                    onLanguageChange = { screenModel.updateBook { copy(language = it) } },
                    pages = state.book.pages?.toString() ?: "",
                    onPagesChange = { screenModel.updateBook { copy(pages = it.toLongOrNull()) } },
                    format = state.book.format ?: "",
                    onFormatChange = { screenModel.updateBook { copy(format = it) } }
                )

                PurchaseSection(
                    purchaseFrom = state.book.purchaseFrom ?: "",
                    onPurchaseFromChange = { screenModel.updateBook { copy(purchaseFrom = it) } },
                    purchasePrice = state.book.purchasePrice?.toString() ?: "",
                    onPurchasePriceChange = { screenModel.updateBook { copy(purchasePrice = it.toLongOrNull()) } },
                    purchaseDate = state.book.purchaseDate ?: 0,
                    onPurchaseDateChange = { screenModel.updateBook { copy(purchaseDate = it) } }
                )

                StatusSection(
                    totalPages = state.book.pages ?: 0,
                    readPages = state.book.readPages ?: 0,
                    onStatusChange = { screenModel.updateBook { copy(readStatus = it) } },
                    onReadPagesChange = { screenModel.updateBook { copy(readPages = it) } },
                )

                Button(
                    onClick = {
                        if (state.book.title.isBlank()) {
                            scope.launch {
                                launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Title cannot be empty",
                                        duration = SnackbarDuration.Indefinite
                                    )
                                }
                                delay(1000L)
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                            return@Button
                        } else {
                            scope.launch {
                                isSaving.value = true
                                if (state.book.coverUri != null) {
                                    val newUri =
                                        moveImageToCoverFolder(context, state.book.coverUri!!.toUri())
                                    screenModel.updateBook { copy(coverUri = newUri.toString()) }
                                }
                                clearTempImageCache(context)

                                screenModel.saveBook()
                                snackbarHostState.showSnackbar(
                                    message = "Book saved",
                                    withDismissAction = true
                                )
                                navigator.pop()
                            }
                        }
                    },
                    enabled = !isSaving.value,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(if (isSaving.value) "Saving..." else "Save")
                }
            }
        }

        BackHandler(
            enabled = true
        ) {
            if (state.hasUnsavedChanges) {
                screenModel.toggleDiscardDialog()
            } else {
                navigator.pop()
            }
        }

        if (state.showDiscardDialog) {
            AlertDialog(
                onDismissRequest = { screenModel.toggleDiscardDialog() },
                title = { Text("Discard Changes?") },
                text = { Text("You have unsaved changes. Are you sure you want to discard them?") },
                confirmButton = {
                    TextButton(onClick = {
                        screenModel.toggleDiscardDialog()
                        navigator.pop()
                    }) {
                        Text("Discard")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { screenModel.toggleDiscardDialog() }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}