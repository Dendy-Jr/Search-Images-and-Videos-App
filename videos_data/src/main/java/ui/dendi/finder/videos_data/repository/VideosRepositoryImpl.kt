package ui.dendi.finder.videos_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.videos_data.local.VideoCache
import ui.dendi.finder.videos_data.local.VideosLocalDataSource
import ui.dendi.finder.videos_data.remote.VideoPagingSource
import ui.dendi.finder.videos_data.remote.VideosRemoteDataSource
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.videos_domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

class VideosRepositoryImpl @Inject constructor(
    private val remoteDataSource: VideosRemoteDataSource,
    private val localDataSource: VideosLocalDataSource,
) : VideosRepository {

    override fun getPagedVideos(
        query: String,
        type: String?,
        category: String?,
        orientation: String?,
        colors: String?
    ): Flow<PagingData<Video>> =
        Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                VideoPagingSource(remoteDataSource, query)
            }
        ).flow

    override fun getFavoritesVideo(): Flow<List<Video>> =
        localDataSource.getVideos().map {
            it.toDomain()
        }


    override suspend fun saveVideoToFavorites(item: Video) {
        localDataSource.insertImage(item)
    }

    override suspend fun deleteVideoFromFavorites(item: Video) {
        localDataSource.deleteImage(item)
    }

    override suspend fun deleteAllVideos() {
        localDataSource.deleteAllVideos()
    }

    private fun List<VideoCache>.toDomain(): List<Video> =
        map { it.toDomain() }

}

@Module
@InstallIn(SingletonComponent::class)
interface VideosRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: VideosRepositoryImpl): VideosRepository
}