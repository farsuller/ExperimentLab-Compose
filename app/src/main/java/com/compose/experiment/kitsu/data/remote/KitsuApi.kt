package com.compose.experiment.kitsu.data.remote

import com.compose.experiment.kitsu.data.remote.dto.AnimeResponseDto
import com.compose.experiment.kitsu.data.remote.dto.TrendingAnimeListResponseDto
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface KitsuApi {

    @GET("trending/anime")
    suspend fun getTrendingAnimeList(): ApiResponse<TrendingAnimeListResponseDto>

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): ApiResponse<AnimeResponseDto>
}