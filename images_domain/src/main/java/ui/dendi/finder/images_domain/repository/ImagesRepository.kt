package ui.dendi.finder.images_domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.base.BaseRepository
import ui.dendi.finder.core.core.models.Image

interface ImagesRepository {

    fun getPagedImages(
        query: String,
        type: String? = null,
        category: String? = null,
        orientation: String? = null,
        colors: String? = null,
    ): Flow<PagingData<Image>>
}