package ui.dendi.finder.favorites_data.videos

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.favorites_domain.videos.repository.FavoritesVideoRepository
import ui.dendi.finder.videos_data.remote.VideosRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

class FavoritesVideoRepositoryImpl @Inject constructor(
    private val remoteDataSource: VideosRemoteDataSource,
) : FavoritesVideoRepository {

    override fun getFavoritesVideo(): Flow<List<Video>> = flow {
        emit(remoteDataSource.getVideos())
    }

    override suspend fun addVideoToFavorites(item: Video) {

    }

    override suspend fun deleteVideoFromFavorites(item: Video) {

    }

    override suspend fun deleteAllFavoritesVideo() {

    }
}

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesVideoRepositoryModule {

    @Binds
    @Singleton
    fun binds(impl: FavoritesVideoRepositoryImpl): FavoritesVideoRepository
}