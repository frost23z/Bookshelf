package com.frost23z.bookshelf.ui.more

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
import com.frost23z.bookshelf.ui.core.components.NavigationBarItemData
import com.frost23z.bookshelf.ui.core.components.NavigationBarItemScreen
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import kotlinx.serialization.Serializable

@Serializable
object MoreScreen : NavigationBarItemScreen {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	override val navigationItemData: NavigationBarItemData
		@Composable
		get() = NavigationBarItemData(
			label = "More",
			icon = Icon.Animated(AnimatedImageVector.animatedVectorResource(R.drawable.anim_more))
		)

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		Scaffold(
			topBar = { TopAppBar(title = { Text("More") }) }
		) { innerPadding ->
			EmptyScreen(message = "MoreScreen", modifier = Modifier.padding(innerPadding))
		}
	}
}