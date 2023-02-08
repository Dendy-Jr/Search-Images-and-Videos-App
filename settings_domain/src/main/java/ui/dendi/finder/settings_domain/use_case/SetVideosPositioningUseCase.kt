package ui.dendi.finder.settings_domain.use_case

import ui.dendi.finder.core.core.models.VideosColumnType
import ui.dendi.finder.settings_domain.repository.VideosPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetVideosPositioningUseCase @Inject constructor(
    private val repository: VideosPositioningRepository,
) {

    suspend operator fun invoke(type: VideosColumnType) {
        repository.setVideosPositioning(type)
    }
}