package com.example.domain.model


object ImageUrlBuilder {
    fun generate(photo: PhotoResult, imageType: ImageSize = ImageSize.THUMBNAIL): String {
        return String.format(BASE_PHOTO_URL, photo.farm, photo.server, photo.id, photo.secret, imageType.value)
    }
    private const val BASE_PHOTO_URL = "https://farm%d.staticflickr.com/%s/%s_%s_%s.jpg"
}

enum class ImageSize(val value: String) {
    SMALL_SQ("s"),
    LARGE_SQ("q"),
    THUMBNAIL("t"),
    x_SMALL("m"),
    SMALL("n"),
    MEDIUM_SMALL("-"),
    MEDIUM("z"),
    MEDIUM_LARGE("c"),
    LARGE("b"),
    X_LARGE("h"),
    XX_LARGE("k"),
    ORIGINAL("o")
}