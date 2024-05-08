package com.reviwh.animanga.model

import com.google.gson.annotations.SerializedName

data class MangaList(

    @field:SerializedName("pagination")
    val pagination: Pagination? = null,

    @field:SerializedName("data")
    val data: List<MangaData?>? = null
)

data class Published(

    @field:SerializedName("string")
    val string: String? = null,

    @field:SerializedName("prop")
    val prop: Prop? = null,

    @field:SerializedName("from")
    val from: String? = null,

    @field:SerializedName("to")
    val to: Any? = null
)

data class AuthorsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Images(

    @field:SerializedName("jpg")
    val jpg: Jpg? = null,

    @field:SerializedName("webp")
    val webp: Webp? = null
)


data class SerializationsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class MangaData(

    @field:SerializedName("title_japanese")
    val titleJapanese: String? = null,

    @field:SerializedName("favorites")
    val favorites: Int? = null,

    @field:SerializedName("chapters")
    val chapters: Any? = null,

    @field:SerializedName("scored_by")
    val scoredBy: Int? = null,

    @field:SerializedName("title_synonyms")
    val titleSynonyms: List<String?>? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("score")
    val score: Any? = null,

    @field:SerializedName("themes")
    val themes: List<ThemesItem?>? = null,

    @field:SerializedName("approved")
    val approved: Boolean? = null,

    @field:SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @field:SerializedName("popularity")
    val popularity: Int? = null,

    @field:SerializedName("members")
    val members: Int? = null,

    @field:SerializedName("title_english")
    val titleEnglish: String? = null,

    @field:SerializedName("rank")
    val rank: Int? = null,

    @field:SerializedName("publishing")
    val publishing: Boolean? = null,

    @field:SerializedName("serializations")
    val serializations: List<SerializationsItem?>? = null,

    @field:SerializedName("images")
    val images: Images? = null,

    @field:SerializedName("volumes")
    val volumes: Any? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("titles")
    val titles: List<TitlesItem?>? = null,

    @field:SerializedName("published")
    val published: Published? = null,

    @field:SerializedName("synopsis")
    val synopsis: String? = null,

    @field:SerializedName("explicit_genres")
    val explicitGenres: List<Any?>? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("scored")
    val scored: Any? = null,

    @field:SerializedName("background")
    val background: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("authors")
    val authors: List<AuthorsItem?>? = null,

    @field:SerializedName("demographics")
    val demographics: List<DemographicsItem?>? = null
)

