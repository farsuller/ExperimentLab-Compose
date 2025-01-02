package com.compose.experiment.fab_explode

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

@Serializable
data object AddItemRoute

const val FAB_EXPLODE_BOUNDS_KEY = "FAB_EXPLODE_BOUNDS_KEY"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FabExplodeScreen(){
    val navController = rememberNavController()
    val fabColor = Color.Green

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = MainRoute,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable<MainRoute> {
                MainScreen(
                    fabColor = fabColor,
                    animatedVisibilityScope = this,
                    onFabClick = {
                        navController.navigate(AddItemRoute)
                    }
                )
            }
            composable<AddItemRoute> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(fabColor)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = FAB_EXPLODE_BOUNDS_KEY
                            ),
                            animatedVisibilityScope = this
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Add item")
                }
            }
        }
    }
}