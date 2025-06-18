package com.compose.experiment.presentations.navigation3

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.compose.experiment.R
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedGraph(navigateToSettings: () -> Unit) {
    val backStack = rememberNavBackStack<BottomNav3Bar>(BottomNav3Bar.Home)

    var currentBottomBarScreen : BottomNav3Bar by rememberSaveable(stateSaver = BottomNav3BarSaver) { mutableStateOf(BottomNav3Bar.Home) }

    val stateHolder = rememberSaveableStateHolder()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nested Graph") },
                actions = {
                    IconButton(
                        onClick = navigateToSettings
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_nav_settings),
                            contentDescription = "Settings Icon"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEach { destination ->
                    NavigationBarItem(
                        selected = currentBottomBarScreen == destination,
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = "$destination Icon"
                            )
                        },
                        onClick = {
                            if(backStack.lastOrNull() != destination){
                                if(backStack.lastOrNull() in bottomBarItems){
                                    backStack.removeAt(backStack.lastIndex)
                                }
                                backStack.add(destination)
                                currentBottomBarScreen = destination
                            }
                        }
                    )
                }
            }
        }
    ) {

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
               // rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<BottomNav3Bar.Home> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = "Home Screen")
                    }
                }
                entry<BottomNav3Bar.Search> {
                    stateHolder.SaveableStateProvider(key = it.title) {
                        val viewModel = viewModel<SearchViewModel>()
                        var number by remember { mutableIntStateOf(0) }

                        val number2 = viewModel.number2

                        LaunchedEffect(Unit) {
                            while(true){
                                delay(2000)
                                number++
                                viewModel.incrementNumber()
                            }
                        }
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = "Search - ($number - $number2)")
                        }
                    }
                }
                entry<BottomNav3Bar.Profile> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = "Profile Screen")
                    }
                }
            }
        )
    }
}