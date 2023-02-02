package ui.dendi.finder.settings_data.repository.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.settings_data.repository.storage.ImagesPositioningStorage
import ui.dendi.finder.settings_domain.repository.ImagesPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

class ImagesPositioningRepositoryImpl @Inject constructor(
    private val storage: ImagesPositioningStorage,
) : ImagesPositioningRepository {

    override suspend fun setItemsPositioning(type: ImagesColumnType) =
        withContext(Dispatchers.IO) {
            storage.layoutManager = type
        }

    override fun getItemsPositioning(): Flow<ImagesColumnType> = flow {
        emit(storage.layoutManager ?: ImagesColumnType.ONE_COLUMN)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface ImagesPositioningRepositoryModule {

    @Singleton
    @Binds
    fun binds(impl: ImagesPositioningRepositoryImpl): ImagesPositioningRepository
}