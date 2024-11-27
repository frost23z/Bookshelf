package com.frost23z.bookshelf.ui.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.ui.core.constants.LargeIcon
import com.frost23z.bookshelf.ui.core.constants.MediumPadding
import com.frost23z.bookshelf.ui.core.constants.SmallPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryBookItem(
    book: Books,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
        modifier =
            modifier
                .background(
                    color =
                        if (isSelected) {
                            Color.Companion.LightGray
                        } else {
                            Color.Companion.Transparent
                        }
                ).combinedClickable(onClick = onClick, onLongClick = onLongClick)
                .fillMaxWidth()
                .padding(
                    horizontal = MediumPadding,
                    vertical = SmallPadding
                )
    ) {
        Image(
            painter =
                if (book.coverUri != null) {
                    rememberAsyncImagePainter(
                        model = book.coverUri
                    )
                } else {
                    rememberVectorPainter(Icons.Outlined.AutoStories)
                },
            contentDescription = if (book.coverUri != null) "Book cover for ${book.title}" else "Default book icon",
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(LargeIcon)
                    .border(
                        border =
                            BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            ),
                        shape = RoundedCornerShape(8.dp)
                    ).clip(RoundedCornerShape(8.dp)),
            colorFilter = if (book.coverUri == null) ColorFilter.tint(MaterialTheme.colorScheme.primary) else null
        )
        Text(
            text = book.title,
            modifier = Modifier.padding(start = MediumPadding)
        )
    }
}