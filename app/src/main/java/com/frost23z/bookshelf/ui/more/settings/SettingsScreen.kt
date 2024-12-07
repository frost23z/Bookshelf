package com.frost23z.bookshelf.ui.more.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.frost23z.bookshelf.ui.core.components.ListItem
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.constants.MediumIcon
import com.frost23z.bookshelf.ui.core.constants.SmallIcon
import com.frost23z.bookshelf.ui.more.settings.userinterface.UISettingsScreen

class SettingsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopBar(
                    title = "Settings",
                    navigateUp = { navigator.pop() },
                    searchEnabled = false
                )
            }
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
                    headlineContent = "Appearance",
                    modifier =
                        Modifier.clickable(
                            onClick = {
                                navigator.push(UISettingsScreen())
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