package com.reviwh.animanga.di

import android.content.Context
import com.reviwh.animanga.data.Repository
import com.reviwh.animanga.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}