package com.frost23z.bookshelf.ui.reading

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.screen.EmptyScreen

object ReadingTab : Tab {
    private fun readResolve(): Any = ReadingTab

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 1u,
                title = "Reading",
                icon =
                    rememberAnimatedVectorPainter(
                        animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.anim_reading),
                        atEnd = LocalTabNavigator.current.current.key == key
                    )
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen("Coming soon")
    }
}