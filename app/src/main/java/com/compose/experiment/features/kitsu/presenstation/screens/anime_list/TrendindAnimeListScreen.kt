package com.compose.experiment.features.kitsu.presenstation.screens.anime_list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.experiment.features.kitsu.presenstation.KitsuViewModel
import com.compose.experiment.features.kitsu.presenstation.components.AnimeCard

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TrendingAnimeListScreen(
    onAnimeClick: (String?, String?) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val viewModel: KitsuViewModel = hiltViewModel()

    val animeList by viewModel.animeList.collectAsStateWithLifecycle()


    Scaffold { innerPadding ->
        AnimatedContent(
            targetState = animeList.isEmpty(),
            label = ""
        ) { isEmpty ->

            if (isEmpty) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(10.dp)
                    )
                }
            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        end = 10.dp,
                        top = 10.dp + innerPadding.calculateTopPadding(),
                        bottom = 10.dp + innerPadding.calculateBottomPadding()
                    )
                ) {
                    item {
                        Text(text = "Trending Anime")
                    }

                    items(animeList) { anime ->

                        AnimeCard(
                            animeData = anime, onClick = {
                                onAnimeClick(
                                    anime.attributes.posterImage?.original ?: "",
                                    anime.id
                                )
                            },
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                }
            }

        }

    }

}