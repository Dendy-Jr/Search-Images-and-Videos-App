package ui.dendi.finder.app.feature_images.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.app.feature_images.domain.Image
import ui.dendi.finder.app.feature_images.domain.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    operator fun invoke(query: String, type: String, category: String): Flow<PagingData<Image>> = repository.getPagedItems(query, type, category)
}