package com.reviwh.animanga.ui.screen.anime

import com.reviwh.animanga.model.AnimeData

data class AnimeState(
    val animeDataList: List<AnimeData?> = emptyList(),
)