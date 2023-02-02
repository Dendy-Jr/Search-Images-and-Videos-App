package ui.dendi.finder.settings_domain.use_case

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.settings_domain.repository.ImagesPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesPositioningUseCase @Inject constructor(
    private val repository: ImagesPositioningRepository,
) {

    operator fun invoke(): Flow<ImagesColumnType> {
        return repository.getItemsPositioning()
    }
}