package com.frost23z.bookshelf.ui.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailScreen(
	state: DetailScreenState,
	onEvent: (DetailScreenEvent) -> Unit,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Text(text = state.book.toString())
	}
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
	DetailScreen(state = DetailScreenState(), onEvent = {})
}