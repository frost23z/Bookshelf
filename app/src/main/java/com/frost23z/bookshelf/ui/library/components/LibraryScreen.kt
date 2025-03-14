package com.frost23z.bookshelf.ui.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LibraryScreen(
	state: LibraryScreenState,
	onEvent: (LibraryScreenEvent) -> Unit,
	modifier: Modifier = Modifier
) {
	LazyColumn(modifier = modifier) {
		items(state.library) { book ->
			LibraryListViewItem(
				headlineContent = book.title,
				leadingContent = book.coverUri,
				modifier = Modifier.clickable { onEvent(LibraryScreenEvent.OpenDetail(book.id)) }
			)
		}
	}
}