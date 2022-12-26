package ui.dendi.finder.favorites_presentation.adapter

import ui.dendi.finder.core.core.multichoice.ImageListItem

interface ImageAdapterListener {
    fun onImageDelete(image: ImageListItem)
    fun onImageChosen(image: ImageListItem)
    fun onImageToggle(image: ImageListItem)
}