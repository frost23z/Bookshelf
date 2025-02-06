package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.constants.IconSize

@Composable
fun TextFieldGroupContainer(
	modifier: Modifier = Modifier,
	content: @Composable ColumnScope.() -> Unit
) {
	Column(
		modifier = modifier.border(
			width = 1.dp,
			color = MaterialTheme.colorScheme.outline,
			shape = RoundedCornerShape(8.dp)
		),
		content = content
	)
}

@Composable
fun TextFieldSeparator(modifier: Modifier = Modifier) {
	HorizontalDivider(
		modifier = modifier.padding(start = IconSize.Large),
		color = MaterialTheme.colorScheme.outline
	)
}