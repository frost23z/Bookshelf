package com.frost23z.bookshelf.ui.reading

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.NavigationBarItemData
import com.frost23z.bookshelf.ui.core.components.NavigationBarItemScreen
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import kotlinx.serialization.Serializable

@Serializable
object ReadingScreen : NavigationBarItemScreen {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	override val navigationItemData: NavigationBarItemData
		@Composable
		get() = NavigationBarItemData(
			label = "Reading",
			icon = Icon.Animated(AnimatedImageVector.animatedVectorResource(R.drawable.anim_reading))
		)

	@Composable
	override fun Content() {
		EmptyScreen(message = "ReadingScreen")
	}
}