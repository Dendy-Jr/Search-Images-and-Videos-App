package ui.dendi.finder.settings_domain.use_case

import ui.dendi.finder.core.core.models.ListColumnType
import ui.dendi.finder.settings_domain.repository.ItemsPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetItemsPositioningUseCase @Inject constructor(
    private val repository: ItemsPositioningRepository,
) {

    suspend operator fun invoke(type: ListColumnType) {
        repository.setItemsPositioning(type)
    }
}