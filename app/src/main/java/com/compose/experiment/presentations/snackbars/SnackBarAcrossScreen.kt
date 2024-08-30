package com.compose.experiment.presentations.snackbars

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.experiment.MainViewModel
import com.compose.experiment.presentations.navigable_list_detail_pane_scaffold.NavigableListDetailPaneScaffoldScreen
import com.compose.experiment.presentations.navigation_suite_scaffold.NavigationSuiteScaffoldScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object ScreenA

@Serializable
data object ScreenB

@Composable
fun SnackBarAcrossScreen() {
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, key1 = snackBarHostState) { event ->
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()

            val result = snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val navController = rememberNavController()
        val viewModel: MainViewModel = hiltViewModel()
        NavHost(
            navController = navController,
            startDestination = ScreenA,
            modifier = Modifier.padding(innerPadding)

        ) {
            composable<ScreenA> {
                NavigableListDetailPaneScaffoldScreen(
                    onClick = { value ->
                        viewModel.showSnackBar(item = value, actionPressed = {
                            navController.navigate(ScreenB)
                        })

                    }
                )
            }
            composable<ScreenB> {
                NavigationSuiteScaffoldScreen(onBackClick = {
                    navController.popBackStack()
                    scope.launch {
                        SnackbarController.sendEvent(
                            event = SnackbarEvent(
                                message = "Back clicked from NavigationSuiteScaffoldScreen",
                            )
                        )
                    }
                })
            }
        }
    }
}