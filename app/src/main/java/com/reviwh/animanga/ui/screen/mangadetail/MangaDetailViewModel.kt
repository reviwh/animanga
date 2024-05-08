package com.reviwh.animanga.ui.screen.mangadetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviwh.animanga.data.Repository
import com.reviwh.animanga.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MangaDetailViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MangaDetailState>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MangaDetailState>> get() = _uiState

    fun getManga(id: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val manga = repository.getMangaById(id).data
                _uiState.value = UiState.Success(
                    MangaDetailState(manga)
                )
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}