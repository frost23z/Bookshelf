package com.frost23z.bookshelf.ui.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.ui.core.components.ListItem
import com.frost23z.bookshelf.ui.core.constants.MediumIcon

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryBookItem(
    book: Books,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    ListItem(
        headlineContent = book.title,
        modifier =
            modifier
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
        leadingIcon = Icons.Outlined.AutoStories,
        leadingIconDescription = "Book icon",
        leadingImageUri = book.coverUri,
        leadingImageDescription = "Book cover for ${book.title}",
        iconModifier =
            Modifier.border(
                width = 1.dp,
                color = LocalContentColor.current,
                shape = RoundedCornerShape(4.dp)
            ),
        leadingIconSize = MediumIcon,
        tonalElevation = if (isSelected) 4.dp else ListItemDefaults.Elevation
    )
}