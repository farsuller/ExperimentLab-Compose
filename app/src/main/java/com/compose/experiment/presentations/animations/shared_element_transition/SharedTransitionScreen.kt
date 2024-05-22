package com.compose.experiment.presentations.animations.shared_element_transition


import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScreen() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                SharedElementListScreen(
                    onItemClick = { resId, text ->
                        navController.navigate("detail/$resId/$text")
                    },
                    animatedVisibilityScope = this
                )
            }

            composable("detail/{resId}/{text}",
                arguments = listOf(
                    navArgument("resId") { type = NavType.IntType },
                    navArgument("text") { type = NavType.StringType }
                )
            ) {
                val resId = it.arguments?.getInt("resId") ?: 0
                val text = it.arguments?.getString("text") ?: ""
                SharedElementDetailScreen(
                    resId = resId,
                    text = text,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}