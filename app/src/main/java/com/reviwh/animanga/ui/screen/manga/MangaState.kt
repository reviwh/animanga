package com.reviwh.animanga.ui.screen.manga

import com.reviwh.animanga.model.MangaData

data class MangaState(
    val mangaDataList: List<MangaData?> = emptyList(),
)
