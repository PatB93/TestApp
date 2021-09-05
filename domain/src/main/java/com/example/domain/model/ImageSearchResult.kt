package com.example.domain.model

data class ImageSearchResult(val page: Int, val totalPages:Int, val photos: List<PhotoResult>)

data class PhotoResult(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String
) {
    fun getUrl(size: ImageSize): String = ImageUrlBuilder.generate(this, size)
}