package com.reviwh.animanga.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reviwh.animanga.data.Repository
import com.reviwh.animanga.di.Injection
import com.reviwh.animanga.ui.screen.anime.AnimeViewModel
import com.reviwh.animanga.ui.screen.animedetail.AnimeDetailViewModel
import com.reviwh.animanga.ui.screen.manga.MangaViewModel
import com.reviwh.animanga.ui.screen.mangadetail.MangaDetailViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AnimeViewModel::class.java) -> {
                AnimeViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MangaViewModel::class.java) -> {
                MangaViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AnimeDetailViewModel::class.java) -> {
                AnimeDetailViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MangaDetailViewModel::class.java) -> {
                MangaDetailViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}