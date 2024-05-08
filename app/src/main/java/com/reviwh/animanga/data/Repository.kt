package com.reviwh.animanga.data

import com.reviwh.animanga.network.ApiService

class Repository private constructor(
    private val apiService: ApiService
) {
    suspend fun getAnime() = apiService.getAnime()
    suspend fun getManga() = apiService.getManga()
    suspend fun getAnimeById(id: Int) = apiService.getAnimeById(id)
    suspend fun getMangaById(id: Int) = apiService.getMangaById(id)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService).also { instance = it }
            }
    }
}