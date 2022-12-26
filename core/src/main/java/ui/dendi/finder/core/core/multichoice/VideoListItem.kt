package ui.dendi.finder.core.core.multichoice

import ui.dendi.finder.core.core.models.Video

data class VideoListItem(
    val video: Video,
    val isChecked: Boolean,
) {
    val comments get() = video.comments
    val downloads get() = video.downloads
    val duration get() = video.duration
    val id get() = video.id
    val likes get() = video.likes
    val pageURL get() = video.pageURL
    val pictureId get() = video.pictureId
    val tags get() = video.tags
    val type get() = video.type
    val user get() = video.user
    val userId get() = video.userId
    val userImageURL get() = video.userImageURL
    val videos get() = video.videos
    val views get() = video.views
}