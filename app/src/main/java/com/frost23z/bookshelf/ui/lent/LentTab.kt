package com.frost23z.bookshelf.ui.lent

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

object LentTab : Tab {
    private fun readResolve(): Any = LentTab

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 2u,
                title = "Lent",
                icon =
                    rememberAnimatedVectorPainter(
                        animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.anim_lent),
                        atEnd = LocalTabNavigator.current.current.key == key
                    )
            )
        }

    @Composable
    override fun Content() {
        EmptyScreen("Coming soon")
    }
}