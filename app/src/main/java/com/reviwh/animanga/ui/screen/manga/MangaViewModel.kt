package com.reviwh.animanga.ui.screen.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviwh.animanga.data.Repository
import com.reviwh.animanga.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MangaViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MangaState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MangaState>> get() = _uiState

    fun getManga() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val mangaList = repository.getManga().data.orEmpty()
                _uiState.value = UiState.Success(
                    MangaState(mangaList),
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}