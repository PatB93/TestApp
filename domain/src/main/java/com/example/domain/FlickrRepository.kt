package com.example.domain

import com.example.domain.model.ImageSearchResult

interface FlickrRepository {

    suspend fun fetchImages(
        queryText: String,
        page: Int,
        resultsPerPage: Int
    ): Result<ImageSearchResult>
}