package com.frost23z.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.frost23z.bookshelf.ui.home.HomeScreen
import com.frost23z.bookshelf.ui.theme.BookshelfTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			BookshelfTheme {
				HomeScreen()
			}
		}
	}
}