package com.frost23z.bookshelf.ui.lent

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                title = "Lent",
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

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    title = "Lent",
                    searchEnabled = false
                )
            }
        ) { innerPadding ->
            if (state.lentBooks.isEmpty()) {
                EmptyScreen(message = "No books are currently lent")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.lentBooks) { book ->
                        LentBookItem(book)
                    }
                }
            }
        }
    }
}

@Composable
private fun LentBookItem(book: Books) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            book.lentTo?.let { lentTo ->
                Text(
                    text = "Lent to: $lentTo",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            book.lentDate?.let { lentDate ->
                val formattedDate =
                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(lentDate))
                Text(
                    text = "Since: $formattedDate",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            book.lentReturned?.let { returnDate ->
                val formattedDate =
                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(returnDate))
                Text(
                    text = "Return by: $formattedDate",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}