package com.frost23z.bookshelf.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.components.Screen
import com.frost23z.bookshelf.ui.detail.components.DetailScreen
import com.frost23z.bookshelf.ui.detail.components.DetailScreenEvent
import com.frost23z.bookshelf.ui.detail.components.DetailScreenModel
import org.koin.androidx.compose.koinViewModel

object DetailScreen : Screen {
	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		val screenModel = koinViewModel<DetailScreenModel>()
		val state by screenModel.state.collectAsStateWithLifecycle()

		Scaffold(
			topBar = {
				TopAppBar(title = { Text("Detail") }, navigationIcon = {
					IconButton(icon = Icons.AutoMirrored.Outlined.ArrowBack, onClick = {
						screenModel.onEvent(
							DetailScreenEvent.Back
						)
					})
				})
			}
		) { innerPadding ->
			DetailScreen(state = state, onEvent = screenModel::onEvent, modifier = Modifier.padding(innerPadding))
		}
	}
}