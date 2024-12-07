package com.frost23z.bookshelf.ui.more.settings.userinterface

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.frost23z.bookshelf.data.Languages
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.ListItem
import com.frost23z.bookshelf.ui.core.components.TopBar
import com.frost23z.bookshelf.ui.core.constants.PaddingMediumLarge
import com.frost23z.bookshelf.ui.core.constants.PaddingSmall
import com.frost23z.bookshelf.ui.core.helpers.maxCutoutPadding
import com.frost23z.bookshelf.ui.theme.ThemeMode
import com.frost23z.bookshelf.ui.theme.themeColorMapping
import java.util.Locale

class UISettingsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = koinScreenModel<UISettingsScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        val themeList = themeColorMapping().keys.toList()
        val lazyListState = rememberLazyListState()

        val configuration = LocalConfiguration.current
        val portraitWidth = minOf(configuration.screenWidthDp, configuration.screenHeightDp).dp
        val cardWidth = (portraitWidth - (PaddingMediumLarge * 2) - (PaddingSmall * 2)) / 3

        Scaffold(
            topBar = {
                TopBar(
                    title = "User Interface",
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
            ) {
                Column(
                    modifier =
                        Modifier
                            .padding(
                                horizontal = maxOf(maxCutoutPadding(), PaddingMediumLarge),
                                vertical = PaddingSmall
                            ),
                    verticalArrangement = Arrangement.spacedBy(PaddingMediumLarge),
                ) {
                    Text("Theme")

                    MultiChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ThemeMode.entries.forEachIndexed { index, mode ->
                            SegmentedButton(
                                checked = state.themeProperties.themeMode == mode,
                                onCheckedChange = { screenModel.updateThemeMode(mode) },
                                shape =
                                    SegmentedButtonDefaults.itemShape(
                                        index = index,
                                        count = ThemeMode.entries.size
                                    )
                            ) {
                                Text(
                                    text =
                                        mode.name
                                            .lowercase()
                                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                                )
                            }
                        }
                    }

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        state = lazyListState,
                        horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
                    ) {
                        items(
                            count = themeList.size,
                            key = { themeList[it] }
                        ) { index ->
                            ThemeCard(
                                theme = themeList[index],
                                isDark = state.themeProperties.isDark,
                                isAmoledDark = state.themeProperties.isAmoledDark,
                                isSelected = state.themeProperties.theme == themeList[index],
                                onClick = {
                                    screenModel.updateTheme(themeList[index])
                                },
                                modifier = Modifier.width(cardWidth)
                            )
                        }
                    }

                    if (state.themeProperties.isDark) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("AMOLED Dark Mode")
                            Switch(
                                checked = state.themeProperties.isAmoledDark,
                                onCheckedChange = { screenModel.toggleAmoledDarkMode(it) }
                            )
                        }
                    }
                }

                ListItem(
                    headlineContent = "Language",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                screenModel.toggleLanguageMenu()
                            }
                )
            }
        }
        // Scroll to selected theme when screen is displayed
        LaunchedEffect(state.themeProperties.theme) {
            val selectedIndex = themeList.indexOf(state.themeProperties.theme)

            if (selectedIndex != -1) {
                val visibleItemCount = lazyListState.layoutInfo.visibleItemsInfo.size
                val centerOffset = (visibleItemCount / 2).coerceAtLeast(0)
                val targetIndex = (selectedIndex - centerOffset).coerceAtLeast(0)

                lazyListState.animateScrollToItem(targetIndex)
            }
        }

        if (state.showLanguageDialog) {
            AlertDialog(
                icon = null,
                title = { Text("Language") },
                text = {
                    Column {
                        Languages.entries.forEach { language ->
                            DropdownMenuItem(
                                text = {
                                    Row {
                                        Icon(
                                            icon =
                                                if (language == state.language) {
                                                    Icons.Default.RadioButtonChecked
                                                } else {
                                                    Icons.Default.RadioButtonUnchecked
                                                }
                                        )
                                        Spacer(Modifier.size(16.dp))
                                        Text(language.displayName)
                                    }
                                },
                                onClick = {
                                    screenModel.updateLanguage(language)
                                    screenModel.toggleLanguageMenu()
                                }
                            )
                        }
                    }
                },
                onDismissRequest = {
                    screenModel.toggleLanguageMenu()
                },
                confirmButton = {
                    Text(text = "Cancel", modifier = Modifier.clickable { screenModel.toggleLanguageMenu() })
                },
                dismissButton = null
            )
        }
    }
}