@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)
package com.compose.experiment.presentations.navigable_list_detail_pane_scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose.experiment.presentations.snackbars.SnackbarController
import com.compose.experiment.presentations.snackbars.SnackbarEvent
import kotlinx.coroutines.launch


@Composable
fun NavigableListDetailPaneScaffoldScreen(onClick: (Int) -> Unit= {}){


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavigableListDetailPaneContent(
            modifier = Modifier.padding(innerPadding),
            onClick = onClick
        )
    }
}
@Composable
fun NavigableListDetailPaneContent(modifier: Modifier = Modifier,
                                   onClick : (Int) -> Unit = {}) {

    val scope = rememberCoroutineScope()

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(100) {
                    Text(
                        "Item $it",
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .clickable {
                                scope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail,
                                        contentKey = "Item $it"
                                    )

                                    if(it % 2 == 0){
                                        SnackbarController.sendEvent(
                                            event = SnackbarEvent(
                                                message = "Item $it clicked",
                                            )
                                        )
                                    }
                                    else onClick(it)
                                }
                            }
                            .padding(16.dp)
                    )
                }
            }
        },
        detailPane = {
            val content = navigator.currentDestination?.pane?.toString() ?: "Select an item"
            AnimatedPane {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = content)
                    Row {
                        AssistChip(
                            onClick = {
                                scope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Extra,
                                        contentKey = "Option 1"
                                    )
                                }

                            },
                            label = {
                                Text(text = "Option 1")
                            }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        AssistChip(
                            onClick = {
                                scope.launch {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Extra,
                                        contentKey = "Option 2"
                                    )
                                }

                            },
                            label = {
                                Text(text = "Option 2")
                            }
                        )
                    }
                }
            }
        },
        extraPane = {
            val content = navigator.currentDestination?.pane?.toString() ?: "Select an option"
            AnimatedPane {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = content)
                }
            }
        }
    )
}