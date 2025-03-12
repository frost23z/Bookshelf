package com.frost23z.bookshelf.ui.addedit

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.addedit.components.AddEditScreen
import com.frost23z.bookshelf.ui.addedit.components.AddEditScreenEvent
import com.frost23z.bookshelf.ui.addedit.components.AddEditScreenModel
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.components.Tab
import com.frost23z.bookshelf.ui.core.components.TabOptions
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@Serializable
object AddEditTab : Tab {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	override val navigationItemData: TabOptions
		@Composable
		get() = TabOptions(
			label = "Add/Edit",
			icon = Icon.Animated(AnimatedImageVector.animatedVectorResource(R.drawable.anim_add))
		)

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		val screenModel = koinInject<AddEditScreenModel>()
		val state by screenModel.state.collectAsStateWithLifecycle()

		Scaffold(
			topBar = {
				TopAppBar(title = { Text("Add/Edit") }, actions = {
					IconButton(
						icon = Icons.Outlined.Save,
						onClick = {
							screenModel.onEvent(AddEditScreenEvent.SaveBook)
						}
					)
				})
			}
		) { innerPadding ->
			AddEditScreen(
				state = state,
				onEvent = screenModel::onEvent,
				modifier = Modifier.padding(innerPadding)
			)
		}
	}
}