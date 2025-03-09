package com.frost23z.bookshelf.ui.library

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.SearchOptions
import com.frost23z.bookshelf.ui.core.components.Tab
import com.frost23z.bookshelf.ui.core.components.TabOptions
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import com.frost23z.bookshelf.ui.core.screen.LoadingScreen
import com.frost23z.bookshelf.ui.library.components.LibraryScreen
import com.frost23z.bookshelf.ui.library.components.LibraryScreenEvent
import com.frost23z.bookshelf.ui.library.components.LibraryScreenModel
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object LibraryTab : Tab {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	override val navigationItemData: TabOptions
		@Composable
		get() = TabOptions(
			label = "Library",
			icon = Icon.Animated(AnimatedImageVector.animatedVectorResource(R.drawable.anim_library))
		)

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		val screenModel = koinInject<LibraryScreenModel>()
		val state by screenModel.state.collectAsStateWithLifecycle()
		Scaffold(
			topBar = {
				TopBar(
					title = "Library",
					searchOptions = SearchOptions(query = state.query, onSearchQueryChange = {
						screenModel.onEvent(LibraryScreenEvent.UpdateQuery(it))
					})
				)
			},
		) { innerPadding ->
			when {
				state.isLoading -> LoadingScreen(modifier = Modifier.padding(innerPadding))
				state.library.isEmpty() -> EmptyScreen(
					icon = Icons.AutoMirrored.Filled.LibraryBooks,
					message = "No books found",
					subtitle = "Add some books to your library to get started",
					modifier = Modifier.padding(innerPadding)
				)
				else -> LibraryScreen(state = state, onEvent = screenModel::onEvent, modifier = Modifier.padding(innerPadding))
			}
		}
	}
}