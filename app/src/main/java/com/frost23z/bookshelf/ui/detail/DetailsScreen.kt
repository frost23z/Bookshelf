package com.frost23z.bookshelf.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.frost23z.bookshelf.data.Books
import org.koin.core.parameter.parametersOf

class DetailsScreen(private val book: Books) : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<DetailsScreenModel> { parametersOf(book) }
        val state by screenModel.state.collectAsStateWithLifecycle()

        Scaffold { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text(text = state.toString())
            }
        }
    }
}