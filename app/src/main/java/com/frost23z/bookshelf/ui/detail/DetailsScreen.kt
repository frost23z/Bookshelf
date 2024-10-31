package com.frost23z.bookshelf.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.ui.core.util.maxCutoutPadding
import com.frost23z.bookshelf.ui.theme.padding
import org.koin.core.parameter.parametersOf

data class DetailsScreen(private val book: Books) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DetailsScreenModel> { parametersOf(book) }
        val state by screenModel.state.collectAsStateWithLifecycle()

        Scaffold { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .padding(
                            maxOf(maxCutoutPadding(), MaterialTheme.padding.medium),
                            vertical = MaterialTheme.padding.small
                        ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter =
                            if (book.coverUri != null) {
                                rememberAsyncImagePainter(
                                    model = book.coverUri
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
                                .clickable { },
                        colorFilter = if (state.book.coverUri == null) ColorFilter.tint(MaterialTheme.colorScheme.primary) else null
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(text = state.book.title, style = MaterialTheme.typography.titleMedium)
                        val authors = state.contributors["Author"]?.joinToString { it } ?: ""
                        if (authors.isNotEmpty()) {
                            Text(text = authors, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}