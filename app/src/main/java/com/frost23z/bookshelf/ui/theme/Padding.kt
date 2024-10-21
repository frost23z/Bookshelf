package com.frost23z.bookshelf.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

class Padding {
    val extraSmall = 4.dp

    val small = 8.dp

    val medium = 16.dp

    val large = 24.dp

    val extraLarge = 32.dp
}

val MaterialTheme.padding: Padding
    get() = Padding()