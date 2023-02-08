package ui.dendi.finder.settings_domain.use_case

import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.settings_domain.repository.ImagesPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetImagesPositioningUseCase @Inject constructor(
    private val repository: ImagesPositioningRepository,
) {

    suspend operator fun invoke(type: ImagesColumnType) {
        repository.setImagesPositioning(type)
    }
}