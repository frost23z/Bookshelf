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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.IconButton
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
	textStyle: TextStyle = TextStyle.textFieldStyle().copy(color = MaterialTheme.colorScheme.onBackground),
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(autoCorrectEnabled = false, imeAction = ImeAction.Next),
	keyboardActions: KeyboardActions = KeyboardActions.Default,
	singleLine: Boolean = true,
	maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
	minLines: Int = 1,
	cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.onBackground),
	label: String? = null,
	placeholder: String? = label,
	labelTextStyle: TextStyle = TextStyle.textFieldLabelStyle(),
	leadingIcon: ImageVector? = null,
	useSpacerIfNoLeadingIcon: Boolean = true,
	trailingIcon: ImageVector? = null,
	trailingIconClick: () -> Unit = {},
	decorationBox: @Composable (
		innerTextField: @Composable () -> Unit,
	) -> Unit = { innerTextField ->
		TextFieldDecorationBox(
			innerTextField = innerTextField,
			value = value,
			label = label,
			labelTextStyle = labelTextStyle,
			placeholder = placeholder,
			textStyle = textStyle,
			leadingIcon = leadingIcon,
			useSpacerIfNoLeadingIcon = useSpacerIfNoLeadingIcon,
			trailingIcon = trailingIcon,
			trailingIconClick = trailingIconClick
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
	label: String?,
	labelTextStyle: TextStyle,
	placeholder: String?,
	textStyle: TextStyle,
	leadingIcon: ImageVector?,
	useSpacerIfNoLeadingIcon: Boolean,
	trailingIcon: ImageVector?,
	trailingIconClick: () -> Unit
) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		leadingIcon?.let { Icon(it, containerSize = IconSize.Large) } ?: if (useSpacerIfNoLeadingIcon) {
			Spacer(modifier = Modifier.size(IconSize.Large))
		} else {
		}
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
		trailingIcon?.let { IconButton(it, onClick = trailingIconClick) } ?: Spacer(modifier = Modifier.size(IconSize.Large))
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
			label = "Label",
			leadingIcon = Icons.Outlined.Title,
			trailingIcon = Icons.Outlined.Title
		)
		TextFieldSeparator()
		TextField(
			value = value,
			onValueChange = { value = it },
			label = "Label",
			placeholder = "Placeholder",
			leadingIcon = Icons.Outlined.People,
			trailingIcon = Icons.Outlined.KeyboardArrowDown
		)
	}
}