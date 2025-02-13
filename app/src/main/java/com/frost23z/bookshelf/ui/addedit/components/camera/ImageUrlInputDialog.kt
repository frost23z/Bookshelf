package com.frost23z.bookshelf.ui.addedit.components.camera

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.components.AlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

@Composable
fun ImageUrlInputDialog(
	onDismiss: () -> Unit,
	onUriEntered: (Uri) -> Unit
) {
	var text by rememberSaveable { mutableStateOf("") }
	var isLoading by rememberSaveable { mutableStateOf(false) }
	var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

	val context = LocalContext.current
	val coroutineScope = rememberCoroutineScope()

	AlertDialog(
		onDismissRequest = onDismiss,
		title = "Enter Image URL",
		onCancel = onDismiss,
		onConfirm = {
			if (text.isEmpty()) {
				errorMessage = "Please enter a URL"
				return@AlertDialog
			}
			if (!text.startsWith("http")) {
				errorMessage = "Invalid URL"
				return@AlertDialog
			}
			isLoading = true
			coroutineScope.launch {
				try {
					val uri = downloadAndSaveImage(context, text)
					onUriEntered(uri)
					onDismiss()
				} catch (e: IOException) {
					errorMessage = "Failed to download image"
				} finally {
					isLoading = false
				}
			}
		}
	) {
		Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
			Text("Please enter the URL of the image:")
			OutlinedTextField(
				value = text,
				onValueChange = { text = it },
				label = { Text("Image URL") },
				placeholder = { Text(text = "Image URL", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
				modifier = Modifier.fillMaxWidth()
			)
			if (isLoading) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
			}
			errorMessage?.let {
				Text(
					text = it,
					color = MaterialTheme.colorScheme.error,
					modifier = Modifier.padding(top = 8.dp)
				)
			}
		}
	}
}

/**
 * Downloads an image from the given URL and saves it to the app's cache directory.
 * Returns the local URI of the saved image.
 */
private suspend fun downloadAndSaveImage(
	context: Context,
	imageUrl: String
): Uri {
	val client = OkHttpClient()
	val request = Request.Builder().url(imageUrl).build()

	val response = withContext(Dispatchers.IO) {
		client.newCall(request).execute()
	}

	if (!response.isSuccessful) {
		throw IOException("Failed to download image: ${response.code}")
	}

	val inputStream = response.body?.byteStream() ?: throw IOException("No content found")
	val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
	file.outputStream().use { outputStream ->
		inputStream.copyTo(outputStream)
	}

	return Uri.fromFile(file)
}