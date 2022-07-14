package ui.dendi.android.finder.feature_images.domain.usecase

import kotlinx.coroutines.flow.Flow
import ui.dendi.android.finder.feature_images.domain.Image
import ui.dendi.android.finder.feature_images.domain.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    operator fun invoke(): Flow<List<Image>> = repository.getItems()
}