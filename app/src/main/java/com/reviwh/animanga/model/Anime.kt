package com.reviwh.animanga.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("data") val data: AnimeData? = null
)
