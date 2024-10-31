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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconDescription: String? = null,
    iconSize: Dp = 24.dp,
    iconTint: Color = LocalContentColor.current,
    containerSize: Dp = iconSize
) {
    Box(
        modifier = modifier.size(containerSize),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            modifier = Modifier.size(iconSize),
            tint = iconTint
        )
    }
}