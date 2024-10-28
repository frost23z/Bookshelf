package com.frost23z.bookshelf.ui.addedit.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.util.IconSize

@Composable
fun FormFields(
    fields: List<FormField> = emptyList(),
    fieldsVisibility: List<Boolean> = List(fields.size) { true },
    addMoreField: @Composable (() -> Unit)? = null
) {
    BorderedContainer(content = {
        val displayValidator =
            if (fields.size == fieldsVisibility.size) fieldsVisibility else List(fields.size) { true }

        fields.forEachIndexed { index, field ->
            if (displayValidator[index]) {
                FormField(field)
                if (index < fields.size - 1 && displayValidator[index + 1]) {
                    FieldDivider()
                }
            }
        }

        addMoreField?.let { adder ->
            if (fields.isNotEmpty()) {
                FieldDivider()
            }
            adder()
        }
    })
}

@Composable
fun FieldDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = IconSize),
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun AddFieldButton(
    label: String,
    onClickAdder: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(onClick = onClickAdder)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.invoke() ?: Spacer(modifier = Modifier.size(IconSize))
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                style = textStyle
            )
        }
    }
}

@Composable
fun FormField(field: FormField) {
    TextField(
        value = field.value,
        onValueChange = field.onValueChange,
        placeholder = field.placeholder,
        label = field.label,
        modifier = field.modifier,
        labelCustom = field.labelCustom,
        enabled = field.enabled,
        readOnly = field.readOnly,
        keyboardOptions = field.keyboardOptions,
        keyboardActions = field.keyboardActions,
        singleLine = field.singleLine,
        maxLines = field.maxLines,
        minLines = field.minLines,
        visualTransformation = field.visualTransformation,
        leadingIcon = field.leadingIcon,
        trailingIcon = field.trailingIcon
    )
}

data class FormField(
    val value: String,
    val onValueChange: (String) -> Unit,
    val placeholder: String?,
    val label: String?,
    val modifier: Modifier = Modifier,
    val labelCustom: @Composable (() -> Unit)? = null,
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val singleLine: Boolean = true,
    val maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    val minLines: Int = 1,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null
)