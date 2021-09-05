package com.example.testapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.ImageSearchRequest
import com.example.domain.model.ImageSearchResult
import com.example.domain.model.PhotoResult
import com.example.domain.model.SearchImages
import com.example.testapplication.ext.getOrAwaitValue
import com.example.testapplication.search.ImageItems
import com.example.testapplication.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchRepository: SearchImages = mock()
    private val subject = SearchViewModel(searchRepository)

    @Test
    fun `search returns list of image items`() = runBlockingTest {
        val image = PhotoResult("id", "owner", "secret", "server", 1, "title")
        val captor = argumentCaptor<ImageSearchRequest>()
        whenever(searchRepository.execute(captor.capture())).thenAnswer{
            Result.success(ImageSearchResult(1, 2, listOf(image)))
        }

        val result = subject.searchImages("query").getOrAwaitValue()

        verify(searchRepository).execute(captor.firstValue)
        assertEquals(result, listOf(ImageItems(image)))
    }

    @Test
    fun `does not repeat previous search`() = runBlockingTest {
        val image = PhotoResult("id", "owner", "secret", "server", 1, "title")
        val captor = argumentCaptor<ImageSearchRequest>()
        whenever(searchRepository.execute(captor.capture())).thenAnswer{
            Result.success(ImageSearchResult(1, 2, listOf(image)))
        }

        val result = subject.searchImages("query").getOrAwaitValue()
        subject.searchImages("query").getOrAwaitValue()

        verify(searchRepository).execute(captor.firstValue)
        assertEquals(result, listOf(ImageItems(image)))
    }

    @Test
    fun `getNextPage re runs search for next page`() = runBlockingTest {
        val image = PhotoResult("id", "owner", "secret", "server", 1, "title")
        val captor = argumentCaptor<ImageSearchRequest>()
        whenever(searchRepository.execute(captor.capture())).thenAnswer{
            Result.success(ImageSearchResult(1, 2, listOf(image)))
        }

        val result = subject.searchImages("query").getOrAwaitValue()
        subject.getNextPage()

        verify(searchRepository).execute(captor.firstValue)
        verify(searchRepository).execute(captor.lastValue)
        assertEquals(result, listOf(ImageItems(image)))
    }

    @Test
    fun `getPreviousPage runs search for previous page`() = runBlockingTest {
        val image = PhotoResult("id", "owner", "secret", "server", 1, "title")
        val captor = argumentCaptor<ImageSearchRequest>()
        whenever(searchRepository.execute(captor.capture())).thenAnswer{
            Result.success(ImageSearchResult(1, 2, listOf(image)))
        }

        val result = subject.searchImages("query").getOrAwaitValue()
        subject.getPreviousPage()

        verify(searchRepository).execute(captor.firstValue)
        verify(searchRepository).execute(captor.lastValue)
        assertEquals(result, listOf(ImageItems(image)))
    }
}