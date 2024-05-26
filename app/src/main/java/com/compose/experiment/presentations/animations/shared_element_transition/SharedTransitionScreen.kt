package com.compose.experiment.presentations.animations.shared_element_transition


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
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
        NavHost(navController = navController, startDestination = ListScreen) {
            // Destination for the list screen
            composable<ListScreen> {
                SharedElementListScreen(
                    onItemClick = { resId, text ->
                        // Navigate to the detail screen when an item is clicked
                        navController.navigate(DetailScreen(resId = resId, text = text))
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
            }
        }
    }
}