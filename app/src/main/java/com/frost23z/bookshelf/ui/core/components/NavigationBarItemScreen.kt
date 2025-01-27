package com.frost23z.bookshelf.ui.core.components

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Icon {
	data class Static(val icon: ImageVector, val selectedIcon: ImageVector) : Icon()

	data class Animated
		@OptIn(ExperimentalAnimationGraphicsApi::class)
		constructor(val animatedIcon: AnimatedImageVector) : Icon()
}

data class NavigationBarItemData(val label: String, val icon: Icon)

interface NavigationBarItemScreen : Screen {
	val navigationItemData: NavigationBarItemData
		@Composable get
}