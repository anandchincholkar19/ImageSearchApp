package com.example.imagesearchapp.api

import com.example.imagesearchapp.model.Unsplash
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
interface UnsplashApi {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = "CLIENT_ID"
    }

    @Headers("Accept-Version: v1")
    @GET("search/photos")
    suspend fun searchPhoto(
        @Query("query") query: String,
        @Query("page") page:Int,
        @Query("per_page") perPage:Int,
        @Query("client_id") clientId:String,
    ): Unsplash
}
