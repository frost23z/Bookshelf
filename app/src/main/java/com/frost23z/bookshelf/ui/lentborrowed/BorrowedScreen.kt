package com.frost23z.bookshelf.ui.lentborrowed

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.Tab
import com.frost23z.bookshelf.ui.core.components.TabOptions
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import kotlinx.serialization.Serializable

@Serializable
object BorrowedScreen : Tab {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	override val navigationItemData: TabOptions
		@Composable
		get() = TabOptions(
			label = "Borrowed",
			icon = Icon.Animated(AnimatedImageVector.animatedVectorResource(R.drawable.anim_lent))
		)

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		Scaffold(
			topBar = { TopAppBar(title = { Text("Borrowed") }) }
		) { innerPadding ->
			EmptyScreen(message = "BorrowedScreen", modifier = Modifier.padding(innerPadding))
		}
	}
}