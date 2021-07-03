package com.example.imagesearchapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imagesearchapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnspalshRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize =  5,
                maxSize =  100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnsplashPageingSource(unsplashApi,query)
            }
        ).liveData
}