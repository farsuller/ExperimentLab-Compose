package com.compose.experiment.features.kitsu.presenstation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.compose.experiment.features.kitsu.presenstation.screens.anime.AnimeScreen
import com.compose.experiment.features.kitsu.presenstation.screens.anime_list.TrendingAnimeListScreen
import kotlinx.serialization.Serializable

@Serializable
data object AnimeListRoute

@Serializable
data class AnimeRoute(val id: String, val coverImage: String)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KitsuNavigation() {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = AnimeListRoute
        ) {
            composable<AnimeListRoute> {
                TrendingAnimeListScreen(
                    onAnimeClick = { cover, id ->
                        navController.navigate(AnimeRoute(id = id ?: "", coverImage = cover ?: ""))
                    },
                    animatedVisibilityScope = this
                )
            }

            composable<AnimeRoute> {
                val args = it.toRoute<AnimeRoute>()
                AnimeScreen(
                    id = args.id,
                    coverImage = args.coverImage,
                    animatedVisibilityScope = this
                )
            }
        }
    }

}