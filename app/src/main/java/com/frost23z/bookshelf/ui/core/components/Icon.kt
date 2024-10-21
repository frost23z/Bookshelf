package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.frost23z.bookshelf.ui.core.util.IconSize

@Composable
fun Icon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    iconTint: Color = LocalContentColor.current
) {
    Box(
        modifier = modifier.size(IconSize),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = iconTint
        )
    }
}