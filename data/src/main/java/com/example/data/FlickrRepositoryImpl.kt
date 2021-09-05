package com.example.data

import com.example.data.modules.Environment
import com.example.data.modules.FlickrService
import com.example.domain.FlickrRepository
import com.example.domain.model.ImageSearchResult
import com.example.domain.model.PhotoResult

class FlickrRepositoryImpl(
    private val environment: Environment,
    private val service: FlickrService
) : FlickrRepository {
    override suspend fun fetchImages(
        queryText: String,
        page: Int,
        resultsPerPage: Int
    ): Result<ImageSearchResult> = call {
        val result = service.fetchImages(environment.token, queryText, page, resultsPerPage).photos
        ImageSearchResult(result.page, result.pages, result.photo.map {
            PhotoResult(
                id = it.id,
                owner = it.owner,
                secret = it.secret,
                server = it.server,
                farm = it.farm,
                title = it.title
            )
        })
    }
}