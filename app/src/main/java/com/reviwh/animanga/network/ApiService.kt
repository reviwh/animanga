package com.reviwh.animanga.network

import com.reviwh.animanga.model.Anime
import com.reviwh.animanga.model.AnimeList
import com.reviwh.animanga.model.Manga
import com.reviwh.animanga.model.MangaList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top/anime")
    suspend fun getAnime(
        @Query("filter") filter: String = "airing",
        @Query("sfw") page: Boolean = true,
    ): AnimeList

    @GET("top/manga")
    suspend fun getManga(
        @Query("type") filter: String = "manga",
        @Query("sfw") page: Boolean = true,
    ): MangaList

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): Anime

    @GET("manga/{id}")
    suspend fun getMangaById(
        @Path("id") id: Int
    ): Manga
}