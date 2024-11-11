package com.compose.experiment.features.kitsu.data.repository

import com.compose.experiment.features.kitsu.data.remote.KitsuApi
import com.compose.experiment.features.kitsu.domain.model.AnimeData
import com.compose.experiment.features.kitsu.domain.repository.KitsuRepository
import com.skydoves.sandwich.onSuccess
import javax.inject.Inject

class KitsuRepositoryImpl @Inject constructor(
    private val api: KitsuApi
) : KitsuRepository {

    override suspend fun getTrendingAnimeList(): List<AnimeData> {
        var animeList: List<AnimeData> = emptyList()
        api.getTrendingAnimeList()
            .onSuccess {
                animeList = data.data.map { it.toModel() }
            }
        return animeList
    }

    override suspend fun getAnimeById(id: Int): AnimeData? {
        var animeData: AnimeData? = null
        api.getAnimeById(id)
            .onSuccess {
                animeData = data.data.toModel()
            }
        return animeData
    }
}