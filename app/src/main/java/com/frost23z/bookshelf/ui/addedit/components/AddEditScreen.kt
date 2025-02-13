package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Note
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.InsertPhoto
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Publish
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.ui.addedit.components.camera.CameraScreen
import com.frost23z.bookshelf.ui.addedit.components.camera.CropImage
import com.frost23z.bookshelf.ui.addedit.components.camera.ImagePicker
import com.frost23z.bookshelf.ui.addedit.components.camera.ImageUrlInputDialog
import com.frost23z.bookshelf.ui.addedit.models.CoverSelectionState
import com.frost23z.bookshelf.ui.addedit.models.DatePickerFor
import com.frost23z.bookshelf.ui.core.components.DatePickerModal
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.components.SingleChoiceDialog
import com.frost23z.bookshelf.ui.core.constants.IconSize
import com.frost23z.bookshelf.ui.core.models.toDisplayString
import com.frost23z.bookshelf.ui.theme.BookshelfTheme
import kotlin.math.roundToLong

@Composable
fun AddEditScreen(
	state: AddEditScreenState,
	onAction: (AddEditScreenAction) -> Unit,
	modifier: Modifier = Modifier
) {
	val animatedValue by animateFloatAsState(
		targetValue = state.book.readPages?.toFloat() ?: 0f,
		animationSpec = tween(durationMillis = 500),
		label = "Float Animation"
	)

	val expandMore = Icons.Outlined.ExpandMore
	val expandLess = Icons.Outlined.ExpandLess

	LazyColumn(
		modifier = modifier.fillMaxSize(),
		contentPadding = PaddingValues(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		item(key = "CoverSection") {
			if (state.book.coverUri == null) {
				Box(
					modifier = Modifier
						.size(120.dp, 180.dp)
						.clip(RoundedCornerShape(8.dp))
						.clickable {
							onAction(
								AddEditScreenAction.UpdateCoverSelectionState(
									CoverSelectionState.SELECT_SOURCE
								)
							)
						}.background(MaterialTheme.colorScheme.secondaryContainer),
					contentAlignment = Alignment.Center
				) {
					Icon(icon = Icons.Outlined.InsertPhoto, iconSize = IconSize.XXLarge)
				}
			} else {
				Image(
					painter = rememberAsyncImagePainter(
						model = state.book.coverUri
					),
					contentDescription = "Selected cover image",
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.size(120.dp, 180.dp)
						.clip(RoundedCornerShape(8.dp))
						.clickable {
							onAction(
								AddEditScreenAction.UpdateCoverSelectionState(
									CoverSelectionState.SELECT_SOURCE
								)
							)
						},
				)
			}

			if (state.book.coverUri == null) {
				TextButton(onClick = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.SELECT_SOURCE)) }) {
					Text(text = "Select Image")
				}
			} else {
				Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
					TextButton(onClick = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.SELECT_SOURCE)) }) {
						Text(text = "Change Image")
					}
					TextButton(onClick = { onAction(AddEditScreenAction.UpdateBook { copy(coverUri = null) }) }) {
						Text(text = "Remove Image")
					}
				}
			}
		}
		item(key = "TitleSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.book.title,
					onValueChange = { onAction(AddEditScreenAction.UpdateBook { copy(title = it) }) },
					label = "Title",
					leadingIcon = Icons.Outlined.Title
				)
				TextFieldSeparator()
				TextField(
					value = state.book.subtitle ?: "",
					onValueChange = { onAction(AddEditScreenAction.UpdateBook { copy(subtitle = it) }) },
					label = "Subtitle"
				)
			}
		}
		item(key = "InfoSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.publisher,
					onValueChange = { onAction(AddEditScreenAction.UpdatePublisher(it)) },
					label = "Publisher",
					leadingIcon = Icons.Outlined.Publish
				)
				TextFieldSeparator()
				TextField(
					value = state.book.publicationDate?.toString() ?: "",
					onValueChange = { },
					label = "Publication Date",
					placeholder = "YYYY-MM-DD",
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.datePickerFor == DatePickerFor.PUBLICATION_DATE) expandLess else expandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleDatePickerVisibility(DatePickerFor.PUBLICATION_DATE)) },
					readOnly = true
				)
				TextFieldSeparator()
				TextField(
					value = state.language,
					onValueChange = { onAction(AddEditScreenAction.UpdateLanguage(it)) },
					label = "Language",
					leadingIcon = Icons.Outlined.Language
				)
				TextFieldSeparator()
				TextField(
					value = state.book.totalPages?.toString() ?: "",
					onValueChange = {
						if (it.length < 5 && it.isDigitsOnly()) {
							onAction(AddEditScreenAction.UpdateBook { copy(totalPages = it.toLongOrNull()) })
						}
					},
					keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
					label = "Total Pages",
					leadingIcon = Icons.Outlined.AutoStories
				)
				TextFieldSeparator()
				TextField(
					value = state.book.format?.toDisplayString() ?: "",
					onValueChange = { },
					label = "Format",
					leadingIcon = Icons.AutoMirrored.Outlined.Note,
					trailingIcon = if (state.datePickerFor == DatePickerFor.PUBLICATION_DATE) expandLess else expandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleDatePickerVisibility(DatePickerFor.PUBLICATION_DATE)) },
					readOnly = true
				)
			}
		}
		item(key = "AcquisitionSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.acquisition?.toDisplayString() ?: "",
					onValueChange = { },
					label = "Acquired Via",
					leadingIcon = Icons.Outlined.Source,
					trailingIcon = if (state.isAcquisitionDialogVisible) expandLess else expandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleAcquisitionDialogVisibility) },
					readOnly = true
				)
				if (state.acquisition != null) {
					TextFieldSeparator()
					TextField(
						value = state.acquiredFrom,
						onValueChange = { onAction(AddEditScreenAction.UpdateAcquiredFrom(it)) },
						label = if (state.acquisition == AcquisitionType.PURCHASED) "Purchased From" else "Received From"
					)
					TextFieldSeparator()
					TextField(
						value = state.book.acquiredDate?.toString() ?: "",
						onValueChange = { },
						label = if (state.acquisition == AcquisitionType.PURCHASED) "Purchased Date" else "Received Date",
						placeholder = "YYYY-MM-DD",
						leadingIcon = Icons.Outlined.CalendarToday,
						trailingIcon = if (state.datePickerFor == DatePickerFor.ACQUIRED_DATE) expandLess else expandMore,
						trailingIconClick = { onAction(AddEditScreenAction.ToggleDatePickerVisibility(DatePickerFor.ACQUIRED_DATE)) },
						readOnly = true
					)
					if (state.acquisition == AcquisitionType.PURCHASED) {
						TextFieldSeparator()
						TextField(
							value = state.book.purchasePrice?.toString() ?: "",
							onValueChange = { onAction(AddEditScreenAction.UpdateBook { copy(purchasePrice = it.toLongOrNull()) }) },
							keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
							label = "Purchase Price"
						)
					}
				}
			}
		}
		item(key = "ReadStatusSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.book.readStatus?.toDisplayString() ?: "",
					onValueChange = { },
					label = "Read Status",
					leadingIcon = Icons.Outlined.AutoStories,
					trailingIcon = if (state.isReadStatusDialogVisible) expandLess else expandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleReadStatusDialogVisibility) },
					readOnly = true
				)
				TextFieldSeparator()
				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					IconButton(
						icon = Icons.Outlined.RemoveCircleOutline,
						onClick = { onAction(AddEditScreenAction.UpdateBook { copy(readPages = state.book.readPages!! - 1) }) },
						enabled = state.book.readPages != null && state.book.readPages > 0
					)
					Box(contentAlignment = Alignment.CenterEnd) {
						Text(text = animatedValue.toInt().toString())
						Text(text = state.book.totalPages?.toString() ?: "0", color = Color.Transparent)
					}
					Slider(
						value = animatedValue,
						onValueChange = { onAction(AddEditScreenAction.UpdateBook { copy(readPages = it.roundToLong()) }) },
						valueRange = 0f..(state.book.totalPages ?: 0).toFloat(),
						steps = calculateSliderSteps(state.book.totalPages ?: 0),
						modifier = Modifier
							.weight(1f)
							.padding(horizontal = 8.dp)
					)
					Text(text = state.book.totalPages?.toString() ?: "0")
					IconButton(
						icon = Icons.Outlined.AddCircleOutline,
						onClick = { onAction(AddEditScreenAction.UpdateBook { copy(readPages = state.book.readPages!! + 1) }) },
						enabled = state.book.readPages != null && state.book.readPages < state.book.totalPages!!
					)
				}
				TextFieldSeparator()
				TextField(
					value = state.book.startReadingDate?.toString() ?: "",
					onValueChange = { },
					label = "Started Reading On",
					placeholder = "YYYY-MM-DD",
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.datePickerFor == DatePickerFor.START_READING_DATE) expandLess else expandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleDatePickerVisibility(DatePickerFor.START_READING_DATE)) },
					readOnly = true
				)
				TextField(
					value = state.book.finishedReadingDate?.toString() ?: "",
					onValueChange = { },
					label = "Finished Reading On",
					placeholder = "YYYY-MM-DD",
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.datePickerFor == DatePickerFor.FINISHED_READING_DATE) expandLess else expandMore,
					trailingIconClick = { onAction(AddEditScreenAction.ToggleDatePickerVisibility(DatePickerFor.FINISHED_READING_DATE)) },
					readOnly = true
				)
			}
		}
		item(key = "DataPreview") { Text("Book: $state") }
	}

	when (state.coverSelectionState) {
		CoverSelectionState.NONE -> {}
		CoverSelectionState.SELECT_SOURCE -> {
			CoverSourceDialog(
				onDismissRequest = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
				onTakePhoto = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.OPEN_CAMERA)) },
				onPickFromGallery = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.OPEN_GALLERY)) },
				onUrlOptionSelected = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.ENTER_URL)) }
			)
		}
		CoverSelectionState.OPEN_CAMERA -> Dialog(
			onDismissRequest = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			properties = DialogProperties(usePlatformDefaultWidth = false)
		) {
			CameraScreen { uri ->
				onAction(AddEditScreenAction.UpdateBook { copy(coverUri = uri) })
				onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.CROP_IMAGE))
			}
		}
		CoverSelectionState.OPEN_GALLERY -> Dialog(
			onDismissRequest = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			properties = DialogProperties(usePlatformDefaultWidth = false)
		) {
			ImagePicker { uri ->
				onAction(AddEditScreenAction.UpdateBook { copy(coverUri = uri) })
				onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.CROP_IMAGE))
			}
		}
		// TODO: Crop Dialog not showing for some reason
		CoverSelectionState.ENTER_URL -> ImageUrlInputDialog(
			onDismiss = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			onUriEntered = { uri ->
				onAction(AddEditScreenAction.UpdateBook { copy(coverUri = uri) })
				onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.CROP_IMAGE))
			}
		)
		CoverSelectionState.CROP_IMAGE -> Dialog(
			onDismissRequest = { onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			properties = DialogProperties(usePlatformDefaultWidth = false)
		) {
			CropImage(state.book.coverUri!!) { uri ->
				onAction(AddEditScreenAction.UpdateBook { copy(coverUri = uri) })
				onAction(AddEditScreenAction.UpdateCoverSelectionState(CoverSelectionState.NONE))
			}
		}
	}

	if (state.datePickerFor != null) {
		DatePickerModal(
			onDateSelected = { date ->
				when (state.datePickerFor) {
					DatePickerFor.PUBLICATION_DATE -> onAction(AddEditScreenAction.UpdateBook { copy(publicationDate = date) })
					DatePickerFor.ACQUIRED_DATE -> onAction(AddEditScreenAction.UpdateBook { copy(acquiredDate = date) })
					DatePickerFor.START_READING_DATE -> onAction(AddEditScreenAction.UpdateBook { copy(startReadingDate = date) })
					DatePickerFor.FINISHED_READING_DATE -> onAction(AddEditScreenAction.UpdateBook { copy(finishedReadingDate = date) })
				}
				onAction(AddEditScreenAction.ToggleDatePickerVisibility(null))
			},
			onDismiss = { onAction(AddEditScreenAction.ToggleDatePickerVisibility(null)) }
		)
	}
	if (state.isFormatDialogVisible) {
		SingleChoiceDialog(
			selectedOption = state.book.format,
			displayString = { it.toDisplayString() },
			onOptionSelected = { onAction(AddEditScreenAction.UpdateBook { copy(format = it) }) },
			onDismissRequest = { onAction(AddEditScreenAction.ToggleFormatDialogVisibility) },
		)
	}
	if (state.isAcquisitionDialogVisible) {
		SingleChoiceDialog(
			selectedOption = state.acquisition,
			displayString = { it.toDisplayString() },
			onOptionSelected = { onAction(AddEditScreenAction.UpdateAcquisition(it)) },
			onDismissRequest = { onAction(AddEditScreenAction.ToggleAcquisitionDialogVisibility) },
		)
	}
	if (state.isReadStatusDialogVisible) {
		SingleChoiceDialog(
			selectedOption = state.book.readStatus,
			displayString = { it.toDisplayString() },
			onOptionSelected = { onAction(AddEditScreenAction.UpdateBook { copy(readStatus = it) }) },
			onDismissRequest = { onAction(AddEditScreenAction.ToggleReadStatusDialogVisibility) },
		)
	}
}

private fun calculateSliderSteps(totalPages: Long): Int = when (totalPages) {
	in 0..1 -> 0
	in 2..50 -> totalPages.toInt() - 1
	else -> 50
}

@PreviewLightDark
@Composable
private fun AddEditScreenPreview() {
	val screenModel by remember { mutableStateOf(AddEditScreenModel()) }
	val state by screenModel.state.collectAsStateWithLifecycle()
	BookshelfTheme {
		Surface {
			AddEditScreen(
				state = state,
				onAction = screenModel::onAction
			)
		}
	}
}