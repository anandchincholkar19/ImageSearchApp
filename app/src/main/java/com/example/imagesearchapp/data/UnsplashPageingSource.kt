package com.example.imagesearchapp.data

import android.util.Log
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearchapp.api.UnsplashApi
import com.example.imagesearchapp.model.Result
import com.example.imagesearchapp.model.Unsplash
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPageingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = unsplashApi.searchPhoto(
                query, position, params.loadSize, UnsplashApi.CLIENT_ID)
            val photos = response.results
            Log.e("photos:", photos.toString())
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Log.e("exception: ", exception.message.toString())
            LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Log.e("httpException: ", httpException.message.toString())
            LoadResult.Error(httpException)
        }
    }
}
