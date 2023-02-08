package ui.dendi.finder.settings_domain.use_case

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.VideosColumnType
import ui.dendi.finder.settings_domain.repository.VideosPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetVideosPositioningUseCase @Inject constructor(
    private val repository: VideosPositioningRepository,
) {

    operator fun invoke(): Flow<VideosColumnType> {
        return repository.getVideosPositioning()
    }
}