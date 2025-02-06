package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.constants.IconSize
import com.frost23z.bookshelf.ui.core.constants.textFieldLabelStyle
import com.frost23z.bookshelf.ui.core.constants.textFieldStyle

@Composable
fun TextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	textStyle: TextStyle = TextStyle.textFieldStyle(),
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = false,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	cursorBrush: Brush = SolidColor(Color.Black),
	placeholder: String? = null,
	label: String? = null,
	labelTextStyle: TextStyle = TextStyle.textFieldLabelStyle(),
	leadingIcon: ImageVector? = null,
	trailingIcon: ImageVector? = null,
	decorationBox: @Composable (
		innerTextField: @Composable () -> Unit,
	) -> Unit = { innerTextField ->
		TextFieldDecorationBox(
			innerTextField = innerTextField,
			value = value,
			textStyle = textStyle,
			placeholder = placeholder,
			label = label,
			labelTextStyle = labelTextStyle,
			leadingIcon = leadingIcon,
			trailingIcon = trailingIcon
		)
	}
) {
	BasicTextField(
		value = value,
		onValueChange = onValueChange,
		modifier = modifier,
		enabled = enabled,
		readOnly = readOnly,
		textStyle = textStyle,
		keyboardOptions = keyboardOptions,
		keyboardActions = keyboardActions,
		singleLine = singleLine,
		maxLines = maxLines,
		minLines = minLines,
		cursorBrush = cursorBrush,
		decorationBox = decorationBox
	)
}

@Composable
private fun TextFieldDecorationBox(
	innerTextField: @Composable () -> Unit,
	value: String,
	textStyle: TextStyle,
	placeholder: String?,
	label: String?,
	labelTextStyle: TextStyle,
	leadingIcon: ImageVector?,
	trailingIcon: ImageVector?
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		leadingIcon?.let { Icon(it, containerSize = IconSize.Large) } ?: Spacer(modifier = Modifier.size(IconSize.Large))
		Column(
			modifier = Modifier.weight(1f),
			verticalArrangement = Arrangement.Center
		) {
			label?.let {
				Text(
					text = it,
					color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
					style = labelTextStyle
				)
			}
			Box {
				if (value.isEmpty() && placeholder != null) {
					Text(
						text = placeholder,
						color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
						style = textStyle
					)
				}
				innerTextField()
			}
		}
		trailingIcon?.let { Icon(it, containerSize = IconSize.Large) } ?: Spacer(modifier = Modifier.size(IconSize.Large))
	}
}

@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {
	var value by remember { mutableStateOf("") }
	TextFieldGroupContainer {
		TextField(
			value = value,
			onValueChange = { value = it },
			placeholder = "Placeholder",
			label = "Label",
			leadingIcon = Icons.Outlined.Title,
			trailingIcon = Icons.Outlined.Title
		)
		TextFieldSeparator()
		TextField(
			value = value,
			onValueChange = { value = it },
			placeholder = "Placeholder",
			label = "Label",
			leadingIcon = Icons.Outlined.People,
			trailingIcon = Icons.Outlined.KeyboardArrowDown
		)
	}
}