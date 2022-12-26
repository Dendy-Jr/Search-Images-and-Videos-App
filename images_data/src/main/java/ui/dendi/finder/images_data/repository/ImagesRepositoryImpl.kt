package ui.dendi.finder.images_data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_data.remote.ImagesPagingSource
import ui.dendi.finder.images_data.remote.ImagesRemoteDataSource
import ui.dendi.finder.images_domain.repository.ImagesRepository
import ui.dendi.finder.images_domain.repository.MultiChoiceImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class ImagesRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImagesRemoteDataSource,
    private val multiChoiceImagesRepository: MultiChoiceImagesRepository,
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
                    category = category ?: "",
                    colors = colors ?: "",
                    orientation = orientation ?: "",
                    query = query ?: "",
                    remoteDataSource = remoteDataSource,
                    type = type ?: "",
                    multiChoiceImagesRepository = multiChoiceImagesRepository,
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