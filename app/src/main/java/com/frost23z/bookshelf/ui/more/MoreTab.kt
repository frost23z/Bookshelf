package com.frost23z.bookshelf.ui.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.compose.rememberAsyncImagePainter
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.core.components.ListItem
import com.frost23z.bookshelf.ui.core.constants.LargeLogo
import com.frost23z.bookshelf.ui.core.constants.MediumIcon
import com.frost23z.bookshelf.ui.core.constants.PaddingLogo
import com.frost23z.bookshelf.ui.core.constants.SmallIcon
import com.frost23z.bookshelf.ui.more.settings.SettingsScreen
import com.frost23z.bookshelf.ui.theme.LocalContextProvider

object MoreTab : Tab {
    private fun readResolve(): Any = MoreTab

    override val options: TabOptions
        @Composable get() {
            return TabOptions(
                index = 2u,
                title = "More",
                icon = rememberVectorPainter(Icons.AutoMirrored.Filled.More),
            )
        }

    @Composable
    override fun Content() {
        val context = LocalContextProvider.current
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val drawable = ContextCompat.getDrawable(context, R.drawable.bookshelf)
                    Image(
                        painter = rememberAsyncImagePainter(drawable),
                        contentDescription = "Logo",
                        modifier =
                            Modifier
                                .padding(PaddingLogo)
                                .size(LargeLogo)
                    )
                    HorizontalDivider()
                }
            },
        ) { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ListItem(
                    headlineContent = "Settings",
                    modifier =
                        Modifier.clickable(
                            onClick = {
                                navigator.push(SettingsScreen())
                            }
                        ),
                    leadingIcon = Icons.Default.Settings,
                    leadingIconDescription = "Settings icon",
                    leadingIconSize = SmallIcon,
                    leadingContentSize = MediumIcon
                )
            }
        }
    }
}