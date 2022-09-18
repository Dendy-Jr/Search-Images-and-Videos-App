package ui.dendi.finder.images_domain.usecase

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.images_domain.repository.ImagesRepository
import ui.dendi.finder.core.core.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    operator fun invoke(): Flow<List<Image>> = repository.getItems()
}