package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.ui.core.constants.LargeIcon

@Composable
fun ListItem(
    headlineContent: String,
    modifier: Modifier = Modifier,
    supportingContent: String? = null,
    leadingImageUri: String? = null,
    leadingImageDescription: String? = null,
    leadingIcon: ImageVector? = null,
    leadingIconDescription: String? = null,
    iconModifier: Modifier = Modifier,
    leadingContentSize: Dp = LargeIcon,
    leadingIconSize: Dp = leadingContentSize
) {
    ListItem(
        headlineContent = { Text(headlineContent) },
        modifier = modifier,
        supportingContent = supportingContent?.let { { Text(it) } },
        leadingContent =
            when {
                leadingImageUri != null -> {
                    {
                        Image(
                            painter = rememberAsyncImagePainter(model = leadingImageUri),
                            contentDescription = leadingImageDescription,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(leadingContentSize).clip(RoundedCornerShape(4.dp))
                        )
                    }
                }
                leadingIcon != null -> {
                    {
                        Icon(
                            icon = leadingIcon,
                            modifier = iconModifier,
                            iconDescription = leadingIconDescription,
                            iconSize = leadingIconSize,
                            containerSize = leadingContentSize
                        )
                    }
                }
                else -> null
            }
    )
}

@Preview
@Composable
private fun ListItemPreviewIcon() {
    LazyColumn {
        items(10) {
            ListItem(
                headlineContent = "Title",
                supportingContent = "Subtitle",
                leadingIcon = Icons.Outlined.AutoStories,
                leadingIconDescription = "Icon description"
            )
        }
    }
}

@Preview
@Composable
private fun ListItemPreviewImage() {
    LazyColumn {
        items(10) {
            ListItem(
                headlineContent = "Title",
                supportingContent = "Subtitle",
                leadingImageUri = "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg",
                leadingImageDescription = "Image description"
            )
        }
    }
}