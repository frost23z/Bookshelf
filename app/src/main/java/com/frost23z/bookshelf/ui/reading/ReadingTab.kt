package com.frost23z.bookshelf.ui.reading

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
import com.frost23z.bookshelf.ui.core.components.TabOptions
import com.frost23z.bookshelf.ui.core.components.Tab
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen
import kotlinx.serialization.Serializable

@Serializable
object ReadingTab : Tab {
	@OptIn(ExperimentalAnimationGraphicsApi::class)
	override val navigationItemData: TabOptions
		@Composable
		get() = TabOptions(
			label = "Reading",
			icon = Icon.Animated(AnimatedImageVector.animatedVectorResource(R.drawable.anim_reading))
		)

	@OptIn(ExperimentalMaterial3Api::class)
	@Composable
	override fun Content() {
		Scaffold(
			topBar = { TopAppBar(title = { Text("Reading") }) }
		) { innerPadding ->
			EmptyScreen(message = "ReadingTab", modifier = Modifier.padding(innerPadding))
		}
	}
}