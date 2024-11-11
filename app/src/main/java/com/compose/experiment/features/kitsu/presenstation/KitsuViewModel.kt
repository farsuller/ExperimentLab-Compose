package com.compose.experiment.features.kitsu.presenstation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.experiment.features.kitsu.domain.model.AnimeData
import com.compose.experiment.features.kitsu.domain.repository.KitsuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitsuViewModel @Inject constructor(
    val repository: KitsuRepository
) : ViewModel() {

    private var _animeList = MutableStateFlow<List<AnimeData>>(emptyList())
    val animeList = _animeList.asStateFlow()

    private var _anime = MutableStateFlow<AnimeData?>(null)
    val anime = _anime.asStateFlow()


    init {
        viewModelScope.launch {
           _animeList.update { repository.getTrendingAnimeList() }
        }
    }

    fun getAnimeById(id : Int){
        viewModelScope.launch {
            _anime.update { repository.getAnimeById(id) }
        }

    }


}