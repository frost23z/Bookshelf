package com.frost23z.bookshelf.ui.core.modifiers

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager

fun Modifier.runOnEnterKeyPressed(action: () -> Unit): Modifier =
    this.onPreviewKeyEvent {
        when (it.key) {
            Key.Enter, Key.NumPadEnter -> {
                action()
                true
            }

            else -> false
        }
    }

fun Modifier.showSoftKeyboard(show: Boolean): Modifier =
    if (show) {
        composed {
            val focusRequester = remember { FocusRequester() }
            var openKeyboard by rememberSaveable { mutableStateOf(show) }
            LaunchedEffect(focusRequester) {
                if (openKeyboard) {
                    focusRequester.requestFocus()
                    openKeyboard = false
                }
            }

            Modifier.focusRequester(focusRequester)
        }
    } else {
        this
    }

@OptIn(ExperimentalLayoutApi::class)
fun Modifier.clearFocusOnSoftKeyboardHide(onFocusCleared: (() -> Unit)? = null,): Modifier =
    composed {
        var isFocused by remember { mutableStateOf(false) }
        var keyboardShowedSinceFocused by remember { mutableStateOf(false) }
        if (isFocused) {
            val imeVisible = WindowInsets.isImeVisible // This is the used experimental API
            val focusManager = LocalFocusManager.current
            LaunchedEffect(imeVisible) {
                if (imeVisible) {
                    keyboardShowedSinceFocused = true
                } else if (keyboardShowedSinceFocused) {
                    focusManager.clearFocus()
                    onFocusCleared?.invoke()
                }
            }
        }

        Modifier.onFocusChanged {
            if (isFocused != it.isFocused) {
                if (isFocused) {
                    keyboardShowedSinceFocused = false
                }
                isFocused = it.isFocused
            }
        }
    }