package com.frost23z.bookshelf.ui.lent

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import com.frost23z.bookshelf.ui.core.screen.LoadingScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LentTab : Tab {
    private fun readResolve(): Any = LentTab

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 2u,
                title = stringResource(R.string.lent),
                icon =
                    rememberAnimatedVectorPainter(
                        animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.anim_lent),
                        atEnd = LocalTabNavigator.current.current.key == key
                    )
            )
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<LentScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val dateFormatter =
            remember {
                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    title = stringResource(R.string.lent),
                    searchEnabled = false
                )
            }
        ) { innerPadding ->
            when {
                state.isLoading -> {
                    LoadingScreen()
                }

                state.lentBooks.isEmpty() -> {
                    EmptyScreen(
                        icon = Icons.Default.Book,
                        message = "Your lending list is empty",
                        subtitle = "Books you lend to others will appear here",
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = innerPadding,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.lentBooks) { book ->
                            LentBookCard(
                                book = book,
                                dateFormatter = dateFormatter,
                                onReturnClick = { screenModel.returnBook(book.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LentBookCard(
    book: Books,
    dateFormatter: SimpleDateFormat,
    onReturnClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            LentBookInfo(
                icon = Icons.Default.Person
            ) {
                Text(
                    text = book.lentTo ?: "--",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LentBookInfo(
                    icon = Icons.Default.DateRange
                ) {
                    Column {
                        Text(
                            text = "Lent: ${book.lentDate?.let { dateFormatter.format(Date(it)) } ?: "--"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Returned: ${book.lentReturned?.let { dateFormatter.format(Date(it)) } ?: "--"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                TextButton(onClick = onReturnClick) {
                    Text("Mark as Returned")
                }
            }
        }
    }
}

@Composable
private fun LentBookInfo(
    icon: ImageVector?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        content()
    }
}