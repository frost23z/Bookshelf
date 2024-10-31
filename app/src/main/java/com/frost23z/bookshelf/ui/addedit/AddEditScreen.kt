package com.frost23z.bookshelf.ui.addedit

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
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
import com.frost23z.bookshelf.ui.core.constants.SmallPadding
import com.frost23z.bookshelf.ui.core.util.maxCutoutPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddEditScreen : Screen {
    @OptIn(InternalVoyagerApi::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AddEditScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        val snackbarHostState = remember { SnackbarHostState() }

        var isSaving = rememberSaveable { mutableStateOf(false) }
        var showDiscardDialog by rememberSaveable { mutableStateOf(false) }

        BackHandler(
            enabled = true
        ) {
            if (state.hasUnsavedChanges) {
                showDiscardDialog = true
            } else {
                bottomSheetNavigator.hide()
            }
        }

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
                            horizontal = maxCutoutPadding(),
                            vertical = SmallPadding
                        ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoverSection(
                    coverUri = state.coverUri,
                    onCoverUriChange = { screenModel.updateCoverUri(it) },
                    navigator = navigator
                )

                TitleSection(
                    titlePrefix = state.titlePrefix ?: "",
                    onTitlePrefixChange = { screenModel.updateTitlePrefix(it) },
                    title = state.title,
                    onTitleChange = { screenModel.updateTitle(it) },
                    titleSuffix = state.titleSuffix ?: "",
                    onTitleSuffixChange = { screenModel.updateTitleSuffix(it) }
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
                    publisher = state.publisher ?: "",
                    onPublisherChange = { screenModel.updatePublisher(it) },
                    language = state.language ?: "",
                    onLanguageChange = { screenModel.updateLanguage(it) },
                    pages = state.pages ?: "",
                    onPagesChange = { screenModel.updatePages(it) },
                    format = state.format ?: "",
                    onFormatChange = { screenModel.updateFormat(it) }
                )

                PurchaseSection(
                    purchaseFrom = state.purchaseFrom ?: "",
                    onPurchaseFromChange = { screenModel.updatePurchaseFrom(it) },
                    purchasePrice = state.purchasePrice ?: "",
                    onPurchasePriceChange = { screenModel.updatePurchasePrice(it) },
                    purchaseDate = state.purchaseDate ?: "",
                    onPurchaseDateChange = { screenModel.updatePurchaseDate(it) }
                )

                StatusSection(
                    totalPages = state.pages?.toLongOrNull() ?: 0,
                    readPages = state.readPages ?: 0,
                    onStatusChange = { screenModel.updateStatus(it) },
                    onReadPagesChange = { screenModel.updateReadPages(it) }
                )

                Button(
                    onClick = {
                        if (state.title.isBlank()) {
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
                                if (state.coverUri != null) {
                                    val newUri =
                                        moveImageToCoverFolder(context, state.coverUri!!)
                                    screenModel.updateCoverUri(newUri)
                                }
                                clearTempImageCache(context)

                                screenModel.addBook()
                                snackbarHostState.showSnackbar(
                                    message = "Book saved",
                                    withDismissAction = true
                                )
                                bottomSheetNavigator.hide()
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
        if (showDiscardDialog) {
            AlertDialog(
                onDismissRequest = { showDiscardDialog = false },
                title = { Text("Discard Changes?") },
                text = { Text("You have unsaved changes. Are you sure you want to discard them?") },
                confirmButton = {
                    TextButton(onClick = {
                        showDiscardDialog = false
                        bottomSheetNavigator.hide()
                    }) {
                        Text("Discard")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDiscardDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}