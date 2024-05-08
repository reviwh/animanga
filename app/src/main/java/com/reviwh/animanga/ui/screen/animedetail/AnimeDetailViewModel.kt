package com.reviwh.animanga.ui.screen.animedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviwh.animanga.data.Repository
import com.reviwh.animanga.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AnimeDetailState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AnimeDetailState>> get() = _uiState

    fun getAnime(id: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val anime = repository.getAnimeById(id).data
                _uiState.value = UiState.Success(
                    AnimeDetailState(anime)
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}