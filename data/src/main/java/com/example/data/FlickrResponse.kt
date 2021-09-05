package com.example.data

import com.squareup.moshi.Json

data class ImageSearchResponse(val photos: PhotoListResponse)

data class PhotoListResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "pages") val pages: Int,
    @Json(name = "perpage") val perPage: Int,
    @Json(name = "total") val total: Int,
    @Json(name = "photo") val photo: List<ImageDetails>
)

data class ImageDetails(
    @Json(name = "id") val id: String,
    @Json(name = "owner") val owner: String,
    @Json(name = "secret") val secret: String,
    @Json(name = "server") val server: String,
    @Json(name = "farm") val farm: Int,
    @Json(name = "title") val title: String,
    @Json(name = "ispublic") val ispublic: Int,
    @Json(name = "isfriend") val isfriend: Int,
    @Json(name = "isfamily") val isfamily: Int
)