package com.compose.experiment.features.kitsu.domain.repository

import com.compose.experiment.features.kitsu.domain.model.AnimeData

interface KitsuRepository {

    suspend fun getTrendingAnimeList() : List<AnimeData>

    suspend fun getAnimeById(id: Int) : AnimeData?
}