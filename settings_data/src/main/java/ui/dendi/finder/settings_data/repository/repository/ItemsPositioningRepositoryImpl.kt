package ui.dendi.finder.settings_data.repository.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ui.dendi.finder.settings_data.repository.storage.ItemsPositioningStorage
import ui.dendi.finder.settings_domain.ItemsPosition
import ui.dendi.finder.settings_domain.repository.ItemsPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

class ItemsPositioningRepositoryImpl @Inject constructor(
    private val storage: ItemsPositioningStorage,
) : ItemsPositioningRepository {

    override suspend fun setItemsPositioning(position: ItemsPosition) =
        withContext(Dispatchers.IO) {
            storage.layoutManager = position
        }

    override fun getItemsPositioning(): Flow<ItemsPosition> = flow {
        emit(storage.layoutManager ?: ItemsPosition.VERTICAL_SINGLE)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface ItemsPositioningRepositoryModule {

    @Singleton
    @Binds
    fun binds(impl: ItemsPositioningRepositoryImpl): ItemsPositioningRepository
}