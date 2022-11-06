package ui.dendi.finder.videos_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.videos_data.remote.VideoPagingSource
import ui.dendi.finder.videos_data.remote.VideosRemoteDataSource
import ui.dendi.finder.videos_domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

class VideosRepositoryImpl @Inject constructor(
    private val remoteDataSource: VideosRemoteDataSource,
) : VideosRepository {

    override fun getPagedVideos(
        category: String?,
        order: String?,
        query: String,
        type: String?,
    ): Flow<PagingData<Video>> =
        Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                VideoPagingSource(
                    category = category,
                    order = order,
                    query = query,
                    remoteDataSource = remoteDataSource,
                    type = type,
                )
            }
        ).flow
}

@Module
@InstallIn(SingletonComponent::class)
interface VideosRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: VideosRepositoryImpl): VideosRepository
}