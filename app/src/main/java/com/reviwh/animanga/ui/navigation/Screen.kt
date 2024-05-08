package com.reviwh.animanga.ui.navigation

sealed class Screen(val route: String) {
    object Anime : Screen("anime")
    object Manga : Screen("manga")
    object About : Screen("about")
    
    object AnimeDetail : Screen("anime/{id}") {
        fun createRoute(id: Int) = "anime/$id"
    }

    object MangaDetail : Screen("manga/{id}") {
        fun createRoute(id: Int) = "manga/$id"
    }

}
