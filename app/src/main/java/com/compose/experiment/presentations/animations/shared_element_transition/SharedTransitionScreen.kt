package com.compose.experiment.presentations.animations.shared_element_transition


import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScreen() {
    // Layout for shared element transitions
    SharedTransitionLayout {
        // Create a NavHost for navigation between different destinations
        val navController = rememberNavController()

        // Define a flag to indicate whether animation is in progress
        var isAnimating by remember { mutableStateOf(false) }



        NavHost(navController = navController, startDestination = ListScreen) {
            // Destination for the list screen
            composable<ListScreen> {
                SharedElementListScreen(
                    onItemClick = { resId, text ->
                        // Navigate to the detail screen when an item is clicked
                        if (!isAnimating) {
                            isAnimating = true

                            navController.navigate(DetailScreen(resId = resId, text = text))
                        }
                    },
                    animatedVisibilityScope = this
                )
            }

            // Destination for the detail screen
            composable<DetailScreen> {
                val args = it.toRoute<DetailScreen>()
                // Extract arguments passed from the list screen
                val resId = args.resId
                val text = args.text
                SharedElementDetailScreen(
                    resId = resId,
                    text = text,
                    animatedVisibilityScope = this
                )

                // Handle back press events
                HandleBackPress {
                    // Detail screen is popped from back stack, do something here
                    isAnimating = false // Reset the isAnimating flag
                    navController.popBackStack() // Navigate back to the list screen
                }
            }
        }
    }
}

@Composable
fun HandleBackPress(
    onBackPressed: () -> Unit
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed() // Execute the provided onBackPressed function
            }
        }
    }
    DisposableEffect(backCallback) {
        onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove() // Remove the callback when the effect is disposed
        }
    }
}