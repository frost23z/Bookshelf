package com.frost23z.bookshelf.ui.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.frost23z.bookshelf.domain.models.BookFormat

@Composable
inline fun <reified T : Enum<T>> SingleChoiceDialog(
	selectedOption: T?,
	crossinline onOptionSelected: (T) -> Unit,
	crossinline displayString: @Composable (T) -> String = { it.toString() },
	noinline onDismissRequest: () -> Unit,
	modifier: Modifier = Modifier,
	properties: DialogProperties = DialogProperties()
) {
	val enumEntries = enumValues<T>().toList()

	AlertDialog(
		onDismissRequest = onDismissRequest,
		properties = properties,
		modifier = modifier,
		onCancel = { onDismissRequest() }
	) {
		Column {
			enumEntries.forEach { value ->
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)).clickable {
						onOptionSelected(value)
						onDismissRequest()
					}
				) {
					RadioButton(
						selected = value == selectedOption,
						onClick = {
							onOptionSelected(value)
							onDismissRequest()
						}
					)
					Spacer(modifier = Modifier.width(8.dp))
					Text(
						text = displayString(value),
						modifier = Modifier.weight(1f)
					)
				}
			}
		}
	}
}

@Preview
@Composable
private fun Choose() {
	var selected by remember { mutableStateOf<BookFormat?>(null) }
	var showDialog by remember { mutableStateOf(false) }

	if (showDialog) {
		SingleChoiceDialog(
			selectedOption = selected,
			onOptionSelected = { selected = it },
			onDismissRequest = { showDialog = false }
		)
	}
	Surface(
		modifier = Modifier.fillMaxSize()
	) {
		Box(
			contentAlignment = Alignment.Center
		) {
			Button(onClick = { showDialog = true }) {
				Text("Show Dialog")
			}
		}
	}
}