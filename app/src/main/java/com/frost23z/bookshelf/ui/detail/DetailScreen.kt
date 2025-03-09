package com.frost23z.bookshelf.ui.addedit

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frost23z.bookshelf.ui.core.components.Screen
import com.frost23z.bookshelf.ui.detail.components.DetailScreenModel
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
class DetailScreen : Screen {
	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		val screenModel = koinInject<DetailScreenModel>()
		val state by screenModel.state.collectAsStateWithLifecycle()

		Scaffold(
			topBar = {
				TopAppBar(title = { Text("Detail") })
			}
		) { innerPadding ->
		}
	}
}