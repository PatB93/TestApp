package com.example.testapplication.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ImageSearchRequest
import com.example.domain.model.ImageSearchResult
import com.example.domain.model.SearchImages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchImages: SearchImages
) : ViewModel() {

    private val imageResponse = MutableLiveData<List<ImageItems>>()
    private val _pageCount = MutableLiveData<Pair<Int, Int>>()
    private var currentQuery: ImageSearchRequest? = null
    val pageCount: LiveData<Pair<Int, Int>> get() = _pageCount

    fun searchImages(query: String): LiveData<List<ImageItems>> {
        viewModelScope.launch {
            if (query != currentQuery?.query) {
                currentQuery = ImageSearchRequest(query, AtomicInteger(1)).also {
                    search(it)
                }
            }
        }
        return imageResponse
    }

    fun getNextPage() {
        currentQuery?.let {
            viewModelScope.launch {
                search(it.nextPage())
            }
        }
    }

    fun getPreviousPage() {
        currentQuery?.let {
            viewModelScope.launch {
                search(it.previousPage())
            }
        }
    }

    private suspend fun search(query: ImageSearchRequest) {
        val result = searchImages.execute(query)
        if (result.isSuccess) {
            val photoResponse = result.getOrDefault(ImageSearchResult(0, 0, listOf()))
            _pageCount.postValue(Pair(photoResponse.page, photoResponse.totalPages))
            val items = photoResponse.photos.map {
                ImageItems(it)
            }
            imageResponse.postValue(items)
        }
    }
}