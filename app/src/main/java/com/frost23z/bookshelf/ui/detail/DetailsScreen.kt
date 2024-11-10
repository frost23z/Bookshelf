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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.frost23z.bookshelf.ui.core.constants.MediumPadding
import com.frost23z.bookshelf.ui.core.constants.SmallIcon
import com.frost23z.bookshelf.ui.core.constants.SmallPadding
import com.frost23z.bookshelf.ui.core.util.maxCutoutPadding
import org.koin.core.parameter.parametersOf

data class DetailsScreen(private val bookId: Long) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DetailsScreenModel> { parametersOf(bookId) }
        val state by screenModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            screenModel.loadDetails()
        }

        var coverDialog by remember { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow

        Scaffold { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .padding(
                            maxOf(maxCutoutPadding(), MediumPadding),
                            vertical = MediumPadding
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
                                .clickable { coverDialog = true },
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
                Button(onClick = { navigator.push(AddEditScreen(true, state.book.id)) }) {
                    Text(text = "Edit Book")
                }
            }
        }
        if (coverDialog) {
            Dialog(
                onDismissRequest = { coverDialog = false },
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