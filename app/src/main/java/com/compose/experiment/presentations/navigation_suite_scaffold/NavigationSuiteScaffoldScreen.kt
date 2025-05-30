package com.compose.experiment.presentations.navigation_suite_scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavigationSuiteScaffoldScreen(onBackClick: () -> Unit = {}) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        var selectedItem by remember { mutableIntStateOf(0) }
        NavigationSuiteScaffold(
            modifier = Modifier.padding(innerPadding),
            navigationSuiteItems = {
                Screen.entries.forEachIndexed { index, screen ->
                    item(
                        selected = index == selectedItem,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) }

                    )

                }
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
               Screen.entries.forEachIndexed { index, screen ->
                   if (index == selectedItem) {
                       Text(text = screen.title)
//                       Button(onClick = onBackClick) {
//                           Text(text = "Back")
//                       }
                   }
               }
            }
        }
    }
}

enum class Screen(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    SEARCH("Search", Icons.Default.Search),
    SETTINGS("Settings", Icons.Default.Settings),
}