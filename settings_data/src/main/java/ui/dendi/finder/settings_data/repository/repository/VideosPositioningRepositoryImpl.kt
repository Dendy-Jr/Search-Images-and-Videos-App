package ui.dendi.finder.settings_data.repository.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ui.dendi.finder.core.core.models.VideosColumnType
import ui.dendi.finder.settings_data.repository.storage.VideosPositioningStorage
import ui.dendi.finder.settings_domain.repository.VideosPositioningRepository
import javax.inject.Inject
import javax.inject.Singleton

class VideosPositioningRepositoryImpl @Inject constructor(
    private val storage: VideosPositioningStorage,
) : VideosPositioningRepository {

    override suspend fun setVideosPositioning(type: VideosColumnType) {
        withContext(Dispatchers.IO) {
            storage.layoutManager = type
        }
    }

    override fun getVideosPositioning(): Flow<VideosColumnType> = flow {
        emit(storage.layoutManager ?: VideosColumnType.ONE_COLUMN)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface VideosPositioningRepositoryModule {

    @Singleton
    @Binds
    fun binds(impl: VideosPositioningRepositoryImpl): VideosPositioningRepository
}