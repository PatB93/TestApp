package com.example.domain.model

import com.example.domain.FlickrRepository
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class SearchImages @Inject constructor(
    private val flickrRepository: FlickrRepository
): UseCase<ImageSearchRequest, ImageSearchResult> {

    override suspend fun execute(params: ImageSearchRequest): Result<ImageSearchResult> {
        return flickrRepository.fetchImages(params.query, params.page.get(), params.resultsPerPage)
    }
}

data class ImageSearchRequest(val query: String, val page: AtomicInteger, val resultsPerPage: Int = 25) {
    fun previousPage(): ImageSearchRequest {
        page.decrementAndGet()
        return this
    }
    fun nextPage(): ImageSearchRequest {
        page.incrementAndGet()
        return this
    }
}