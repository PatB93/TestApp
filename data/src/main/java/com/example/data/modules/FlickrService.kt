package com.example.data.modules

import com.example.data.ImageSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrService {

    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    suspend fun fetchImages(
        @Query("api_key") token: String,
        @Query("text") queryText: String,
        @Query("page") page: Int,
        @Query("per_page") resultsPerPage: Int
    ): ImageSearchResponse
}