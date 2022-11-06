package ui.dendi.finder.images_domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.images_domain.repository.ImagesRepository
import ui.dendi.finder.core.core.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    operator fun invoke(
        category: String,
        colors: String,
        orientation: String,
        query: String,
        type: String,
    ): Flow<PagingData<Image>> =
        repository.getPagedImages(
            category = category,
            colors = colors,
            orientation = orientation,
            query = query,
            type = type,
        )
}