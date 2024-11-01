package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.RemoveCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.modifiers.clearFocusOnSoftKeyboardHide
import com.frost23z.bookshelf.ui.core.modifiers.runOnEnterKeyPressed
import com.frost23z.bookshelf.ui.core.modifiers.showSoftKeyboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    // The title of the top bar
    title: String?,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    titleContent: @Composable () -> Unit = { TopBarTitle(title = title, subtitle = subtitle) },
    // Actions to be displayed on the top bar
    navigateUp: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    // Selection counter and actions. Set selectionCounter to enable selection
    selectionCounter: Int = 0,
    onCancelSelection: () -> Unit = {},
    selectionActions: @Composable (RowScope.() -> Unit) = {},
    // Search field and actions. Set searchQuery to enable search
    searchEnabled: Boolean = true,
    searchQuery: String? = null,
    onSearchQueryChange: (String?) -> Unit = {},
    onClickCancelSearch: () -> Unit = { onSearchQueryChange(null) },
    // visual customization
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    color: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val isSelectionActive = selectionCounter > 0

    TopAppBar(
        modifier = modifier,
        title =
            when {
                isSelectionActive -> {
                    { TopBarTitle(title = selectionCounter.toString()) }
                }

                searchQuery != null -> {
                    {
                        TopSearchField(
                            searchQuery = searchQuery,
                            onSearchQueryChange = onSearchQueryChange
                        )
                    }
                }

                else -> titleContent
            },
        navigationIcon = {
            when {
                isSelectionActive -> {
                    IconButton(
                        onClick = onCancelSelection,
                        icon = Icons.Default.Close,
                        tooltip = "Cancel",
                        iconDescription = "Cancel selection"
                    )
                }

                searchQuery != null -> {
                    IconButton(
                        onClick = onClickCancelSearch,
                        icon = Icons.AutoMirrored.Outlined.ArrowBack,
                        tooltip = "Cancel search",
                        iconDescription = "Cancel search"
                    )
                }

                else -> {
                    navigateUp?.let {
                        IconButton(
                            onClick = navigateUp,
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            tooltip = "Navigate up",
                            iconDescription = "Navigate up"
                        )
                    }
                }
            }
        },
        actions = {
            when {
                isSelectionActive -> {
                    selectionActions()
                }

                searchQuery == null && searchEnabled -> {
                    key("search") {
                        IconButton(
                            onClick = { onSearchQueryChange("") },
                            icon = Icons.Outlined.Search,
                            tooltip = "Search"
                        )
                    }
                    key("actions") { actions() }
                }

                !searchQuery.isNullOrBlank() -> {
                    key("search") {
                        //
                        IconButton(
                            onClick = { onSearchQueryChange("") },
                            icon = Icons.Outlined.Close,
                            tooltip = "Clear"
                        )
                    }
                    key("actions") { actions() }
                }

                else -> {
                    actions()
                }
            }
        },
        windowInsets = windowInsets,
        colors =
            if (isSelectionActive) {
                color.copy(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            } else {
                color
            },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun TopBarTitle(
    title: String?,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Column(modifier = modifier) {
        title?.let {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        subtitle?.let {
            Text(
                text = it,
                modifier = Modifier.basicMarquee(), // This is the used experimental API
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun TopBarTitlePreview() {
    TopBarTitle(
        title = "A long title. A long title. A long title. A long title.A long title. A long title.",
        subtitle = "A long subtitle. A long subtitle. A long subtitle. A long subtitle. A long subtitle. A long subtitle."
    )
}

@Composable
fun TopSearchField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String? = null,
    onSearch: (String) -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val searchAndClearFocus = {
        if (searchQuery.isNotBlank()) {
            onSearch(searchQuery)
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    BasicTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .focusRequester(focusRequester)
                .runOnEnterKeyPressed { searchAndClearFocus() }
                .showSoftKeyboard(remember { searchQuery.isEmpty() })
                .clearFocusOnSoftKeyboardHide(),
        textStyle =
            textStyle.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { searchAndClearFocus() }),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            if (searchQuery.isBlank()) {
                Text(
                    text = placeholderText ?: "Search...",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            innerTextField()
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF6DFDF)
@Composable
private fun TopSearchFieldPreview() {
    var searchQuery by remember { mutableStateOf("") }
    TopSearchField(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        placeholderText = "Search..."
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFFF6DFDF)
@Composable
private fun TopSearchBarPreview() {
    var searchQuery by remember { mutableStateOf<String?>(null) }
    var selectionCounter by remember { mutableIntStateOf(0) }

    TopBar(
        title = "Bookshelf",
        subtitle = "A simple bookshelf app",
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        selectionCounter = selectionCounter,
        onCancelSelection = { selectionCounter = 0 },
        actions = {
            IconButton(
                onClick = {
                    selectionCounter++
                },
                icon = Icons.Outlined.AddCircle,
                tooltip = "IncreaseSelectionCounter"
            )
        },
        selectionActions = {
            IconButton(
                onClick = {
                    selectionCounter++
                },
                icon = Icons.Outlined.AddCircle,
                tooltip = "IncreaseSelectionCounter"
            )
            IconButton(
                onClick = {
                    selectionCounter--
                },
                icon = Icons.Outlined.RemoveCircle,
                tooltip = "DecreaseSelectionCounter"
            )
        },
        navigateUp = { selectionCounter-- }
    )
}