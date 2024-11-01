package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconTextButtonVertical(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    iconSize: Dp = 24.dp,
    text: String,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    tint: Color? = null,
    spacing: Dp = 4.dp
) {
    Column(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                ).clickable(onClick = onClick),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            modifier = Modifier.size(iconSize),
            tint = tint ?: LocalContentColor.current
        )
        Spacer(modifier = Modifier.size(spacing))
        Text(
            text = text,
            color = tint ?: LocalContentColor.current
        )
    }
}

@Preview
@Composable
private fun IconTextButtonPreview() {
    IconTextButtonVertical(
        icon = Icons.Default.Add,
        onClick = {},
        text = "Text",
        iconDescription = "Icon",
        iconSize = 24.dp,
        modifier = Modifier,
        tint = Color.Black
    )
}