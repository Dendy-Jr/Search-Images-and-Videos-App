package ui.dendi.finder.core.core.multichoice

import ui.dendi.finder.core.core.models.Image

data class ImageListItem(
    val image: Image,
    val isChecked: Boolean,
) {
    val collections get() = image.collections
    val comments get() = image.comments
    val downloads get() = image.downloads
    val id get() = image.id
    val imageHeight get() = image.imageHeight
    val imageSize get() = image.imageSize
    val imageWidth get() = image.imageWidth
    val largeImageURL get() = image.largeImageURL
    val likes get() = image.likes
    val pageURL get() = image.pageURL
    val previewHeight get() = image.previewHeight
    val previewURL get() = image.previewURL
    val previewWidth get() = image.previewWidth
    val tags get() = image.tags
    val type get() = image.type
    val user get() = image.user
    val userImageURL get() = image.userImageURL
    val views get() = image.views
    val webFormatHeight get() = image.webFormatHeight
    val webFormatURL get() = image.webFormatURL
    val webFormatWidth get() = image.webFormatWidth
}