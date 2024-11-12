package com.frost23z.bookshelf.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.addedit.AddEditScreen
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.constants.MediumIcon
import com.frost23z.bookshelf.ui.core.constants.MediumPadding
import com.frost23z.bookshelf.ui.core.constants.SmallIcon
import com.frost23z.bookshelf.ui.core.constants.SmallPadding
import com.frost23z.bookshelf.ui.core.heplers.maxCutoutPadding
import org.koin.core.parameter.parametersOf

data class DetailsScreen(private val bookId: Long) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DetailsScreenModel> { parametersOf(bookId) }
        val state by screenModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            screenModel.loadDetails()
        }

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopBar(
                    title = "Details",
                    navigateUp = { navigator.pop() },
                    actions = {
                        IconButton(
                            icon = Icons.Default.Delete,
                            onClick = { screenModel.toggleDeleteConfirmationDialog() },
                            iconDescription = "Edit",
                            tooltip = "Edit",
                        )
                        IconButton(
                            icon = Icons.Default.Edit,
                            onClick = { navigator.push(AddEditScreen(true, state.book.id)) },
                            iconDescription = "Edit",
                            tooltip = "Edit",
                        )
                    },
                    searchEnabled = false
                )
            }
        ) { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .padding(
                            maxOf(maxCutoutPadding(), MediumPadding),
                            vertical = SmallPadding
                        ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(SmallPadding)
                ) {
                    Image(
                        painter =
                            if (state.book.coverUri != null) {
                                rememberAsyncImagePainter(
                                    model = state.book.coverUri
                                )
                            } else {
                                painterResource(id = R.drawable.ic_launcher_foreground)
                            },
                        contentDescription = "Select Image",
                        contentScale = ContentScale.Crop,
                        modifier =
                            Modifier
                                .size(100.dp, 150.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { screenModel.toggleCoverDialog() },
                        colorFilter =
                            if (state.book.coverUri == null) {
                                ColorFilter.tint(
                                    MaterialTheme.colorScheme.primary
                                )
                            } else {
                                null
                            }
                    )
                    Column {
                        Text(text = state.book.title, style = MaterialTheme.typography.titleLarge)
                        val authors = state.contributors["Author"]?.joinToString { it } ?: ""
                        if (authors.isNotEmpty()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(SmallPadding),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    icon = Icons.Default.Person,
                                    iconDescription = "Authors",
                                    iconSize = SmallIcon
                                )
                                Text(text = authors, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
                Text(text = state.book.toString())
            }
        }

        if (state.showDeleteConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { screenModel.toggleDeleteConfirmationDialog() },
                icon = {
                    Icon(
                        icon = Icons.Default.Delete,
                        iconDescription = "Select Image",
                        iconSize = MediumIcon
                    )
                },
                title = { Text(text = "Delete Book") },
                text = { Text(text = "Are you sure you want to delete this book?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            screenModel.deleteBook()
                            navigator.pop()
                        }
                    ) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { screenModel.toggleDeleteConfirmationDialog() }) {
                        Text(text = "No")
                    }
                },
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
            )
        }

        if (state.showCoverDialog) {
            Dialog(
                onDismissRequest = { screenModel.toggleCoverDialog() },
                properties =
                    DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                        usePlatformDefaultWidth = false
                    )
            ) {
                Image(
                    painter =
                        if (state.book.coverUri != null) {
                            rememberAsyncImagePainter(
                                model = state.book.coverUri
                            )
                        } else {
                            painterResource(id = R.drawable.ic_launcher_foreground)
                        },
                    contentDescription = "Cover Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}