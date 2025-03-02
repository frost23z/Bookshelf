package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
	title: String,
	modifier: Modifier = Modifier,
	navigationIcon: IconButtonData? = null,
	actions: List<IconButtonData> = emptyList(),
	expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
	windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
	colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
	scrollBehavior: TopAppBarScrollBehavior? = null
) {
	TopAppBar(
		title = { Text(text = title) },
		modifier = modifier,
		navigationIcon = { navigationIcon?.let { IconButton(icon = navigationIcon.icon, onClick = navigationIcon.onClick) } },
		actions = {
			actions.forEach { action ->
				IconButton(icon = action.icon, onClick = action.onClick)
			}
		},
		expandedHeight = expandedHeight,
		windowInsets = windowInsets,
		colors = colors,
		scrollBehavior = scrollBehavior
	)
}

data class IconButtonData(
	val icon: ImageVector,
	val onClick: () -> Unit
)