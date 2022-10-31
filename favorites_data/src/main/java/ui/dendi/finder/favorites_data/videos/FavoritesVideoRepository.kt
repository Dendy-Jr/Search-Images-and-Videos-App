package ui.dendi.finder.favorites_data.videos

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.favorites_domain.videos.repository.FavoritesVideoRepository
import javax.inject.Inject
import javax.inject.Singleton

class FavoritesVideoRepositoryImpl @Inject constructor(
    private val localDataSource: VideosLocalDataSource,
) : FavoritesVideoRepository {

    override fun getFavoritesVideo(): Flow<List<Video>> =
        localDataSource.getVideos()

    override suspend fun saveVideoToFavorites(item: Video) {
        localDataSource.insertImage(item)
    }

    override suspend fun deleteVideoFromFavorites(item: Video) {
        localDataSource.deleteImage(item)
    }

    override suspend fun deleteAllVideos() {
        localDataSource.deleteAllVideos()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesVideoRepositoryModule {

    @Binds
    @Singleton
    fun binds(impl: FavoritesVideoRepositoryImpl): FavoritesVideoRepository
}