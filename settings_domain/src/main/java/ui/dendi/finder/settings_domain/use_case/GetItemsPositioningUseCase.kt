package ui.dendi.finder.settings_domain.use_case

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.settings_domain.ItemsPosition
import ui.dendi.finder.settings_domain.repository.ItemsPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetItemsPositioningUseCase @Inject constructor(
    private val repository: ItemsPositioningRepository,
) {

    operator fun invoke(): Flow<ItemsPosition> {
        return repository.getItemsPositioning()
    }
}