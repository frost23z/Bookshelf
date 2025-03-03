package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.frost23z.bookshelf.ui.addedit.components.TextField

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
	scrollBehavior: TopAppBarScrollBehavior? = null,
	searchOptions: SearchOptions? = null
) {
	TopAppBar(
		title = {
			when {
				searchOptions?.query != null -> TextField(
					value = searchOptions.query,
					onValueChange = searchOptions.onSearchQueryChange,
					placeholder = "Search...",
					trailingIcon = Icons.Outlined.Close,
					trailingIconClick = { searchOptions.onSearchQueryChange("") }
				)
				else -> {
					Text(text = title)
				}
			}
		},
		modifier = modifier,
		navigationIcon = {
			when {
				searchOptions?.query != null -> IconButton(icon = Icons.AutoMirrored.Outlined.ArrowBack, onClick = searchOptions.onCancelSearch)
				else -> navigationIcon?.let { IconButton(icon = navigationIcon.icon, onClick = navigationIcon.onClick) }
			}
		},
		actions = {
			if (searchOptions != null && searchOptions.query == null) {
				IconButton(icon = Icons.Outlined.Search, onClick = { searchOptions.onSearchQueryChange("") })
			}
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

data class SearchOptions(
	val query: String? = null,
	val onSearchQueryChange: (String?) -> Unit,
	val onCancelSearch: () -> Unit = { onSearchQueryChange(null) }
)

data class IconButtonData(
	val icon: ImageVector,
	val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun TopBarPreview() {
	var searchQuery by remember { mutableStateOf<String?>(null) }
	TopBar(
		title = "Title",
		actions = listOf(IconButtonData(Icons.Outlined.Add, {}), IconButtonData(Icons.Outlined.Remove, {})),
		searchOptions = SearchOptions(
			query = searchQuery,
			onSearchQueryChange = { searchQuery = it }
		)
	)
}