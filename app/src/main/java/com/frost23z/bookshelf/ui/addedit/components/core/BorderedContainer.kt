package com.frost23z.bookshelf.ui.addedit.components.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.constants.MediumPadding
import com.frost23z.bookshelf.ui.core.constants.SmallPadding

@Composable
fun BorderedContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier =
            modifier
                .padding(MediumPadding, SmallPadding)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}
