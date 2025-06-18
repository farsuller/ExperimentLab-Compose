package com.compose.experiment.presentations.navigation3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay


@Composable
fun RootGraph(){
    val backStack = rememberNavBackStack<ScreenNav3>(ScreenNav3.Auth)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<ScreenNav3.Auth>{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Button(onClick = { backStack.add(ScreenNav3.NestedGraph) }
                    ) {
                        Text(text = "Sign in")
                    }
                }
            }
            entry<ScreenNav3.Settings>{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Button(onClick = { backStack.removeLastOrNull() }){
                        Text(text = "Navigate Back")
                    }
                }
            }

            entry<ScreenNav3.NestedGraph> {
                NestedGraph(navigateToSettings = { backStack.add(ScreenNav3.Settings) })
            }
        }
    )
}