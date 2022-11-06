package ui.dendi.finder.images_data.repository

import androidx.paging.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.images_data.remote.ImagesPagingSource
import ui.dendi.finder.images_data.remote.ImagesRemoteDataSource
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_domain.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class ImagesRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImagesRemoteDataSource,
) : ImagesRepository {

    override fun getPagedImages(
        category: String?,
        colors: String?,
        orientation: String?,
        query: String,
        type: String?,
    ): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                ImagesPagingSource(
                    category = query,
                    colors = category ?: "",
                    orientation = category ?: "",
                    query = orientation ?: "",
                    remoteDataSource = remoteDataSource,
                    type = colors ?: "",
                )
            },
        ).flow
    }

    private companion object {
        const val PAGE_SIZE = 30
    }
}

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
interface ImagesRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: ImagesRepositoryImpl): ImagesRepository
}