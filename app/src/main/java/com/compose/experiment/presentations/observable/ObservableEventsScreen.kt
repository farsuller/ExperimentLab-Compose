package com.compose.experiment.presentations.observable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compose.experiment.LoginState
import com.compose.experiment.MainViewModel
import com.compose.experiment.NavigationEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun ObservableEventsScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    )
    {
        composable("login") {
            val viewModel : MainViewModel = hiltViewModel()
            val state = viewModel.state


            ObserveAsEvents(flow = viewModel.navigationEventsChannelFlow) { events ->
                when (events) {
                    is NavigationEvent.NavigateToProfile -> {
                        navController.navigate("profile")
                    }
                }
            }

            LoginScreen(
                state = state,
                onLoginClick = viewModel::login
            )
        }

        composable("profile") {
            ProfileScreen()
        }
    }
}

@Composable
// A helper function to observe a Flow as a series of events.
private fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    // Get the current lifecycle owner from the context.
    val lifecycleOwner = LocalLifecycleOwner.current

    // Launch a coroutine that will run as long as the lifecycle is in a specific state.
    LaunchedEffect(flow, lifecycleOwner.lifecycle) {
        // Repeat the block when the lifecycle is in the STARTED state.
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // Switch to the Main dispatcher immediately to collect the flow and handle each emitted item with the onEvent function.
            withContext(Dispatchers.Main.immediate){
                // Collect the flow and handle each emitted item with the onEvent function.
                flow.collect(onEvent)
            }
        }
    }
}

@Composable
fun LoginScreen(state: LoginState, onLoginClick: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = { onLoginClick() }) {
            Text(text = "Login")
        }
        if(state.isLoading){
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile")
    }
}