package com.compose.experiment.kitsu.domain.repository

import com.compose.experiment.kitsu.domain.model.AnimeData

interface KitsuRepository {

    suspend fun getTrendingAnimeList() : List<AnimeData>

    suspend fun getAnimeById(id: Int) : AnimeData?
}