package com.example.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.modules.Environment
import com.example.data.modules.FlickrService
import com.example.domain.model.ImageSearchResult
import com.example.domain.model.PhotoResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FlickrRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val environment: Environment = Environment("token", "baseUrl", "shortUrl")
    private val service: FlickrService = mock()

    private val subject = FlickrRepositoryImpl(environment, service)

    @Test
    fun `fetches images and translates images to ImageSearchResult`() = runBlocking {
        val photoResult = PhotoResult("", "", "", "", 1, "")
        val imageDetails = ImageDetails("", "", "", "", 1, "", 1, 1, 1)
        val imageSearchResult = ImageSearchResult(1, 1, listOf(photoResult))
        val imageSearchResponse = ImageSearchResponse(PhotoListResponse(1, 1, 1, 1, listOf(imageDetails)))
        whenever(service.fetchImages(environment.token, "query", 1, 1)).thenAnswer {
            imageSearchResponse
        }

        val result = subject.fetchImages("query", 1, 1)

        verify(service).fetchImages("token", "query", 1, 1)
        assertEquals(result, Result.success(imageSearchResult))
    }
}