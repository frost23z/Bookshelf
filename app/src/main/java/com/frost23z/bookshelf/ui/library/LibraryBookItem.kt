package com.frost23z.bookshelf.ui.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.data.Books
import com.frost23z.bookshelf.ui.core.constants.MediumPadding
import com.frost23z.bookshelf.ui.core.constants.SmallPadding
import com.frost23z.bookshelf.ui.core.util.IconSize

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
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier =
                Modifier
                    .size(IconSize)
                    .border(
                        border =
                            BorderStroke(
                                width = 1.dp,
                                brush =
                                    Brush.Companion.linearGradient(
                                        colors =
                                            listOf(
                                                Color.Companion.Magenta,
                                                Color.Companion.Cyan
                                            )
                                    )
                            ),
                        shape = RoundedCornerShape(2.dp)
                    )
        )
        Text(
            text = book.title,
            modifier = Modifier.padding(start = MediumPadding)
        )
    }
}