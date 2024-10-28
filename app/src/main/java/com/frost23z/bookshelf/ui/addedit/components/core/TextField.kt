package com.frost23z.bookshelf.ui.addedit.components.core

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.frost23z.bookshelf.ui.core.util.GetTextStyle
import com.frost23z.bookshelf.ui.core.util.IconSize

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String?,
    label: String?,
    modifier: Modifier = Modifier,
    labelCustom: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = GetTextStyle.fieldStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingIcon?.invoke() ?: Spacer(modifier = Modifier.size(IconSize))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    label?.let {
                        TextFieldText(
                            text = it,
                            textStyle = GetTextStyle.labelStyle,
                        )
                    }
                    labelCustom?.invoke()
                    Box {
                        if (value.isEmpty() && placeholder != null) {
                            TextFieldText(
                                text = placeholder,
                                textStyle = GetTextStyle.fieldStyle
                            )
                        }
                        innerTextField()
                    }
                }
                trailingIcon?.invoke() ?: Spacer(modifier = Modifier.size(IconSize))
            }
        }
    )
}

@Composable
fun TextFieldText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    alpha: Float = 0.5f
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha),
        style = textStyle
    )
}
