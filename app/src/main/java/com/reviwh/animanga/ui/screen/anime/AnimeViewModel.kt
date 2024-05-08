package com.reviwh.animanga.ui.screen.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviwh.animanga.data.Repository
import com.reviwh.animanga.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AnimeState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AnimeState>> get() = _uiState

    fun getAnime() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val animeList = repository.getAnime().data.orEmpty()

                _uiState.value = UiState.Success(
                    AnimeState(
                        animeList,
                    )
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}