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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
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
import com.frost23z.bookshelf.ui.core.constants.ExtraLargeIcon
import com.frost23z.bookshelf.ui.core.constants.MediumIcon
import com.frost23z.bookshelf.ui.core.constants.PaddingMediumLarge
import com.frost23z.bookshelf.ui.core.constants.PaddingSmall
import com.frost23z.bookshelf.ui.core.constants.SmallIcon
import com.frost23z.bookshelf.ui.core.helpers.formatDateFromTimestamp
import org.koin.core.parameter.parametersOf

data class DetailsScreen(
    private val bookId: Long
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DetailsScreenModel> { parametersOf(bookId) }
        val state by screenModel.state.collectAsStateWithLifecycle()

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.details),
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
                        .padding(horizontal = PaddingMediumLarge, vertical = PaddingSmall),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cover, Title section and Authors of the book
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (state.book.coverUri) {
                        null -> {
                            Icon(
                                icon = Icons.Default.AutoStories,
                                iconDescription = "Book Cover",
                                iconSize = ExtraLargeIcon,
                                modifier = Modifier.size(120.dp, 180.dp).clip(RoundedCornerShape(8.dp))
                            )
                        }
                        else -> {
                            Image(
                                painter = rememberAsyncImagePainter(model = state.book.coverUri),
                                contentDescription = "Book Cover",
                                contentScale = ContentScale.Crop,
                                modifier =
                                    Modifier
                                        .size(
                                            120.dp,
                                            180.dp
                                        ).clip(RoundedCornerShape(8.dp))
                                        .clickable { screenModel.toggleCoverDialog() }
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val fullTitle = ((state.book.titlePrefix ?: " ") + state.book.title + (state.book.titleSuffix ?: " ")).trim()
                        Text(
                            text = fullTitle,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                icon = Icons.Default.Person,
                                iconDescription = "Authors",
                                iconSize = SmallIcon
                            )
                            state.contributors["Author"]?.let { authors ->
                                Text(
                                    text = authors.joinToString(separator = ", "),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                // Basic Information
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BookSection(title = "Basic Information") {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                InfoRow("Publisher", state.book.publisher)
                                InfoRow("Language", state.book.language)
                                InfoRow("Format", state.book.format)
                                InfoRow("Total Pages", state.book.totalPages?.toString())
                            }
                        }
                    }
                }

                // Purchase Information
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BookSection(title = "Purchase Information") {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                InfoRow("Purchased From", state.book.purchaseFrom)
                                InfoRow("Price", state.book.purchasePrice?.toString())
                                InfoRow(
                                    "Purchase Date",
                                    state.book.purchaseDate?.let { formatDateFromTimestamp(it) }
                                )
                            }
                        }
                    }
                }

                // Reading Status
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BookSection(title = "Reading Progress") {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                InfoRow("Status", state.book.readStatus?.toString() ?: "Not Started")
                                InfoRow(
                                    "Pages Read",
                                    if (state.book.readPages != null) {
                                        "${state.book.readPages}/${state.book.totalPages ?: "?"}"
                                    } else {
                                        "--"
                                    }
                                )
                                InfoRow(
                                    "Started Reading",
                                    state.book.startReadingDate?.let { formatDateFromTimestamp(it) }
                                )
                                InfoRow(
                                    "Finished Reading",
                                    state.book.finishedReadingDate?.let { formatDateFromTimestamp(it) }
                                )
                            }
                        }
                    }
                }

                // Lending Information
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        BookSection(title = "Lending Information") {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                InfoRow("Lent To", state.book.lentTo)
                                InfoRow(
                                    "Lent Date",
                                    state.book.lentDate?.let { formatDateFromTimestamp(it) }
                                )
                                InfoRow(
                                    "Returned",
                                    state.book.lentReturned?.let { formatDateFromTimestamp(it) }
                                )
                            }
                        }
                    }
                }

                // Other Contributors
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.contributors.forEach { (role, contributors) ->
                            if (role != "Author") {
                                BookSection(title = role) {
                                    Text(
                                        text = contributors.joinToString(separator = ", "),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
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
                properties =
                    DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
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
                            rememberVectorPainter(Icons.Default.AutoStories)
                        },
                    contentDescription = "Cover Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        LaunchedEffect(key1 = state.book.id) {
            screenModel.refresh()
        }
    }
}

@Composable
private fun BookSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        content()
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value ?: "--",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}