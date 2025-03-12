package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.domain.models.AcquisitionType
import com.frost23z.bookshelf.domain.models.Books
import com.frost23z.bookshelf.domain.repositories.AddEditRepository
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
import com.frost23z.bookshelf.ui.core.constants.Padding
import com.frost23z.bookshelf.ui.core.models.toDisplayString
import com.frost23z.bookshelf.ui.core.navigation.Navigator
import com.frost23z.bookshelf.ui.theme.BookshelfTheme
import org.koin.compose.koinInject
import kotlin.math.roundToLong

@Composable
fun AddEditScreen(
	state: AddEditScreenState,
	onEvent: (AddEditScreenEvent) -> Unit,
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
		contentPadding = PaddingValues(Padding.Medium),
		verticalArrangement = Arrangement.spacedBy(Padding.Medium),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		item(key = "CoverSection") {
			Box(
				modifier = Modifier
					.size(120.dp, 180.dp)
					.clip(RoundedCornerShape(8.dp))
					.clickable {
						onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.SELECT_SOURCE))
					}.background(MaterialTheme.colorScheme.secondaryContainer),
				contentAlignment = Alignment.Center
			) {
				SubcomposeAsyncImage(
					model = ImageRequest
						.Builder(LocalContext.current)
						.data(state.book.coverUri)
						.crossfade(true)
						.build(),
					contentDescription = "Book Cover",
					loading = { CircularProgressIndicator() },
					error = { Icon(icon = Icons.Outlined.InsertPhoto, iconSize = IconSize.XXLarge) },
					contentScale = ContentScale.Crop,
				)
			}

			if (state.book.coverUri == null) {
				TextButton(onClick = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.SELECT_SOURCE)) }) {
					Text(text = stringResource(R.string.select_image))
				}
			} else {
				Row(horizontalArrangement = Arrangement.spacedBy(Padding.XSmall)) {
					TextButton(onClick = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.SELECT_SOURCE)) }) {
						Text(text = stringResource(R.string.change_image))
					}
					TextButton(onClick = { onEvent(AddEditScreenEvent.UpdateBook { copy(coverUri = null) }) }) {
						Text(text = stringResource(R.string.remove_image))
					}
				}
			}
		}
		item(key = "TitleSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.book.title,
					onValueChange = { onEvent(AddEditScreenEvent.UpdateBook { copy(title = it) }) },
					label = stringResource(R.string.title),
					leadingIcon = Icons.Outlined.Title
				)
				TextFieldSeparator()
				TextField(
					value = state.book.subtitle ?: "",
					onValueChange = { onEvent(AddEditScreenEvent.UpdateBook { copy(subtitle = it) }) },
					label = stringResource(R.string.subtitle)
				)
			}
		}
		item(key = "InfoSection") {
			TextFieldGroupContainer {
				TextFieldSuggestion(
					value = state.publisher,
					onValueChange = { onEvent(AddEditScreenEvent.UpdatePublisher(it)) },
					label = stringResource(R.string.publisher),
					placeholder = stringResource(R.string.publisher),
					leadingIcon = Icons.Outlined.Publish,
					suggestion = state.publisherSuggestions
				)
				TextFieldSeparator()
				TextField(
					value = state.book.publicationDate?.toString() ?: "",
					onValueChange = { },
					label = stringResource(R.string.publication_date),
					placeholder = stringResource(R.string.yyyy_mm_dd),
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.datePickerFor == DatePickerFor.PUBLICATION_DATE) expandLess else expandMore,
					trailingIconClick = { onEvent(AddEditScreenEvent.ShowDatePickerFor(DatePickerFor.PUBLICATION_DATE)) },
					readOnly = true
				)
				TextFieldSeparator()
				TextField(
					value = state.language,
					onValueChange = { onEvent(AddEditScreenEvent.UpdateLanguage(it)) },
					label = stringResource(R.string.language),
					leadingIcon = Icons.Outlined.Language
				)
				TextFieldSeparator()
				TextField(
					value = state.book.totalPages?.toString() ?: "",
					onValueChange = {
						if (it.isBlank()) {
							onEvent(AddEditScreenEvent.UpdateBook { copy(totalPages = null, readPages = null) })
						} else if (it.length < 5 && it.isDigitsOnly()) {
							if (it.toLong() < (state.book.readPages ?: 0)) {
								onEvent(AddEditScreenEvent.UpdateBook { copy(totalPages = it.toLong(), readPages = it.toLong()) })
							} else {
								onEvent(AddEditScreenEvent.UpdateBook { copy(totalPages = it.toLong()) })
							}
						}
					},
					keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
					label = stringResource(R.string.total_pages),
					leadingIcon = Icons.Outlined.AutoStories
				)
				TextFieldSeparator()
				TextField(
					value = state.book.format?.toDisplayString() ?: "",
					onValueChange = { },
					label = stringResource(R.string.format),
					leadingIcon = Icons.AutoMirrored.Outlined.Note,
					trailingIcon = if (state.datePickerFor == DatePickerFor.PUBLICATION_DATE) expandLess else expandMore,
					trailingIconClick = { onEvent(AddEditScreenEvent.ShowDialogFor(DialogFor.FORMAT)) },
					readOnly = true
				)
			}
		}
		item(key = "AcquisitionSection") {
			TextFieldGroupContainer {
				TextField(
					value = state.acquisition?.toDisplayString() ?: "",
					onValueChange = { },
					label = stringResource(R.string.acquired_via),
					leadingIcon = Icons.Outlined.Source,
					trailingIcon = if (state.dialogFor == DialogFor.ACQUISITION_METHOD) expandLess else expandMore,
					trailingIconClick = { onEvent(AddEditScreenEvent.ShowDialogFor(DialogFor.ACQUISITION_METHOD)) },
					readOnly = true
				)
				if (state.acquisition != null) {
					TextFieldSeparator()
					TextField(
						value = state.acquiredFrom,
						onValueChange = { onEvent(AddEditScreenEvent.UpdateAcquiredFrom(it)) },
						label = if (state.acquisition == AcquisitionType.PURCHASED) "Purchased From" else "Received From"
					)
					TextFieldSeparator()
					TextField(
						value = state.book.acquiredDate?.toString() ?: "",
						onValueChange = { },
						label = if (state.acquisition == AcquisitionType.PURCHASED) "Purchased Date" else "Received Date",
						placeholder = stringResource(R.string.yyyy_mm_dd),
						leadingIcon = Icons.Outlined.CalendarToday,
						trailingIcon = if (state.datePickerFor == DatePickerFor.ACQUIRED_DATE) expandLess else expandMore,
						trailingIconClick = { onEvent(AddEditScreenEvent.ShowDatePickerFor(DatePickerFor.ACQUIRED_DATE)) },
						readOnly = true
					)
					if (state.acquisition == AcquisitionType.PURCHASED) {
						TextFieldSeparator()
						TextField(
							value = state.book.purchasePrice?.toString() ?: "",
							onValueChange = { onEvent(AddEditScreenEvent.UpdateBook { copy(purchasePrice = it.toLongOrNull()) }) },
							keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
							label = stringResource(R.string.purchase_price)
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
					label = stringResource(R.string.read_status),
					leadingIcon = Icons.Outlined.AutoStories,
					trailingIcon = if (state.dialogFor == DialogFor.READ_STATUS) expandLess else expandMore,
					trailingIconClick = { onEvent(AddEditScreenEvent.ShowDialogFor(DialogFor.READ_STATUS)) },
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
						onClick = { onEvent(AddEditScreenEvent.UpdateBook { copy(readPages = state.book.readPages!! - 1) }) },
						enabled = state.book.readPages != null && state.book.readPages > 0
					)
					Box(contentAlignment = Alignment.CenterEnd) {
						Text(text = animatedValue.toInt().toString())
						Text(text = state.book.totalPages?.toString() ?: "0", color = Color.Transparent)
					}
					Slider(
						value = animatedValue,
						onValueChange = { onEvent(AddEditScreenEvent.UpdateBook { copy(readPages = it.roundToLong()) }) },
						valueRange = 0f..(state.book.totalPages ?: 0).toFloat(),
						steps = calculateSliderSteps(state.book.totalPages ?: 0),
						modifier = Modifier
							.weight(1f)
							.padding(horizontal = 8.dp)
					)
					Text(text = state.book.totalPages?.toString() ?: "0")
					IconButton(
						icon = Icons.Outlined.AddCircleOutline,
						onClick = { onEvent(AddEditScreenEvent.UpdateBook { copy(readPages = state.book.readPages!! + 1) }) },
						enabled = state.book.readPages != null && state.book.readPages < state.book.totalPages!!
					)
				}
				TextFieldSeparator()
				TextField(
					value = state.book.startReadingDate?.toString() ?: "",
					onValueChange = { },
					label = stringResource(R.string.started_reading_on),
					placeholder = stringResource(R.string.yyyy_mm_dd),
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.datePickerFor == DatePickerFor.START_READING_DATE) expandLess else expandMore,
					trailingIconClick = { onEvent(AddEditScreenEvent.ShowDatePickerFor(DatePickerFor.START_READING_DATE)) },
					readOnly = true
				)
				TextField(
					value = state.book.finishedReadingDate?.toString() ?: "",
					onValueChange = { },
					label = stringResource(R.string.finished_reading_on),
					placeholder = stringResource(R.string.yyyy_mm_dd),
					leadingIcon = Icons.Outlined.CalendarToday,
					trailingIcon = if (state.datePickerFor == DatePickerFor.FINISHED_READING_DATE) expandLess else expandMore,
					trailingIconClick = { onEvent(AddEditScreenEvent.ShowDatePickerFor(DatePickerFor.FINISHED_READING_DATE)) },
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
				onDismissRequest = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
				onTakePhoto = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.OPEN_CAMERA)) },
				onPickFromGallery = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.OPEN_GALLERY)) },
				onUrlOptionSelected = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.ENTER_URL)) }
			)
		}
		CoverSelectionState.OPEN_CAMERA -> Dialog(
			onDismissRequest = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			properties = DialogProperties(usePlatformDefaultWidth = false)
		) {
			CameraScreen { uri ->
				onEvent(AddEditScreenEvent.UpdateBook { copy(coverUri = uri) })
				onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.CROP_IMAGE))
			}
		}
		CoverSelectionState.OPEN_GALLERY -> Dialog(
			onDismissRequest = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			properties = DialogProperties(usePlatformDefaultWidth = false)
		) {
			ImagePicker { uri ->
				onEvent(AddEditScreenEvent.UpdateBook { copy(coverUri = uri) })
				onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.CROP_IMAGE))
			}
		}
		// TODO: Crop Dialog not showing for some reason
		CoverSelectionState.ENTER_URL -> ImageUrlInputDialog(
			onDismiss = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			onUriEntered = { uri ->
				onEvent(AddEditScreenEvent.UpdateBook { copy(coverUri = uri) })
				onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.CROP_IMAGE))
			}
		)
		CoverSelectionState.CROP_IMAGE -> Dialog(
			onDismissRequest = { onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.NONE)) },
			properties = DialogProperties(usePlatformDefaultWidth = false)
		) {
			CropImage(state.book.coverUri!!) { uri ->
				onEvent(AddEditScreenEvent.UpdateBook { copy(coverUri = uri) })
				onEvent(AddEditScreenEvent.UpdateCoverSelectionState(CoverSelectionState.NONE))
			}
		}
	}

	if (state.datePickerFor != null) {
		DatePickerModal(
			onDateSelected = { date ->
				when (state.datePickerFor) {
					DatePickerFor.PUBLICATION_DATE -> onEvent(AddEditScreenEvent.UpdateBook { copy(publicationDate = date) })
					DatePickerFor.ACQUIRED_DATE -> onEvent(AddEditScreenEvent.UpdateBook { copy(acquiredDate = date) })
					DatePickerFor.START_READING_DATE -> onEvent(AddEditScreenEvent.UpdateBook { copy(startReadingDate = date) })
					DatePickerFor.FINISHED_READING_DATE -> onEvent(AddEditScreenEvent.UpdateBook { copy(finishedReadingDate = date) })
				}
				onEvent(AddEditScreenEvent.ShowDatePickerFor(null))
			},
			onDismiss = { onEvent(AddEditScreenEvent.ShowDatePickerFor(null)) }
		)
	}
	if (state.dialogFor != null) {
		when (state.dialogFor) {
			DialogFor.FORMAT -> {
				SingleChoiceDialog(
					selectedOption = state.book.format,
					displayString = { it.toDisplayString() },
					onOptionSelected = { onEvent(AddEditScreenEvent.UpdateBook { copy(format = it) }) },
					onDismissRequest = { onEvent(AddEditScreenEvent.ShowDialogFor(null)) }
				)
			}
			DialogFor.ACQUISITION_METHOD -> {
				SingleChoiceDialog(
					selectedOption = state.acquisition,
					displayString = { it.toDisplayString() },
					onOptionSelected = { onEvent(AddEditScreenEvent.UpdateAcquisition(it)) },
					onDismissRequest = { onEvent(AddEditScreenEvent.ShowDialogFor(null)) }
				)
			}
			DialogFor.READ_STATUS -> {
				SingleChoiceDialog(
					selectedOption = state.book.readStatus,
					displayString = { it.toDisplayString() },
					onOptionSelected = { onEvent(AddEditScreenEvent.UpdateBook { copy(readStatus = it) }) },
					onDismissRequest = { onEvent(AddEditScreenEvent.ShowDialogFor(null)) }
				)
			}
		}
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
	class FakeAddEditRepository : AddEditRepository {
		override suspend fun insert(
			books: Books,
			publisher: String
		) {
			// For preview purposes
		}
	}
	val navigator = koinInject<Navigator>()
	val screenModel by remember { mutableStateOf(AddEditScreenModel(FakeAddEditRepository(), navigator)) }
	val state by screenModel.state.collectAsStateWithLifecycle()
	BookshelfTheme {
		Surface {
			AddEditScreen(
				state = state,
				onEvent = screenModel::onEvent
			)
		}
	}
}