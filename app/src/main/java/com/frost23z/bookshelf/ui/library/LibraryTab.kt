package com.frost23z.bookshelf.ui.library

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.R

object LibraryTab : Tab {
    @OptIn(ExperimentalAnimationGraphicsApi::class)
    override val options: TabOptions
        @Composable get() {
            LocalTabNavigator.current.current.key == key
            return TabOptions(
                index = 0u,
                title = "Library",
                icon = rememberAnimatedVectorPainter(
                    animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.anim_library_select),
                    atEnd = LocalTabNavigator.current.current.key == key
                )
            )
        }

    @Composable
    override fun Content() {

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(
            topBar = {
                // TODO: TopBar
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            // TODO: Content
        }
    }
}