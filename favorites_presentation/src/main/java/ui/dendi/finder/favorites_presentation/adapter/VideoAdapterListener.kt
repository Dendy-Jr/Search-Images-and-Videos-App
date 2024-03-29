package ui.dendi.finder.favorites_presentation.adapter

import ui.dendi.finder.core.core.multichoice.VideoListItem

interface VideoAdapterListener {
    fun onVideoDelete(video: VideoListItem)
    fun onVideoChosen(video: VideoListItem)
    fun onVideoToggle(video: VideoListItem)
}