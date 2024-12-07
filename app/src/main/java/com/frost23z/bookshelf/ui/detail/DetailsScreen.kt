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
                        state.contributors["Author"]?.let { authors ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    icon = Icons.Default.Person,
                                    iconDescription = "Authors",
                                    iconSize = SmallIcon
                                )
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
                                state.book.publisher?.let { InfoRow("Publisher", it) }
                                state.book.language?.let { InfoRow("Language", it) }
                                state.book.format?.let { InfoRow("Format", it) }
                                state.book.totalPages?.let { InfoRow("Total Pages", it.toString()) }
                            }
                        }
                    }
                }

                // Purchase Information
                if (state.book.purchaseFrom != null || state.book.purchasePrice != null || state.book.purchaseDate != null) {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            BookSection(title = "Purchase Information") {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    state.book.purchaseFrom?.let { InfoRow("Purchased From", it) }
                                    state.book.purchasePrice?.let { InfoRow("Price", it.toString()) }
                                    state.book.purchaseDate?.let { InfoRow("Purchase Date", it.toString()) }
                                }
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
                                state.book.readPages?.let { InfoRow("Pages Read", "$it/${state.book.totalPages ?: "?"}") }
                                state.book.startReadingDate?.let { InfoRow("Started Reading", it.toString()) }
                                state.book.finishedReadingDate?.let { InfoRow("Finished Reading", it.toString()) }
                            }
                        }
                    }
                }

                // Lending Information
                if (state.book.isLent) {
                    Card {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            BookSection(title = "Lending Information") {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    state.book.lentTo?.let { InfoRow("Lent To", it) }
                                    state.book.lentDate?.let { InfoRow("Lent Date", it.toString()) }
                                    state.book.lentReturned?.let { InfoRow("Returned", it.toString()) }
                                }
                            }
                        }
                    }
                }

                // Other Contributors
                state.contributors.forEach { (role, contributors) ->
                    if (role != "Author") {
                        Card {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                BookSection(title = role) {
                                    Text(
                                        text = contributors.joinToString(),
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
    if (value != null) {
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
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}