package ui.dendi.finder.images_domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Image

interface ImagesRepository {

    fun getPagedImages(
        category: String? = null,
        colors: String? = null,
        orientation: String? = null,
        query: String,
        type: String? = null,
    ): Flow<PagingData<Image>>
}