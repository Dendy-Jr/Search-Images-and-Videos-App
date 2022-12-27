package ui.dendi.finder.favorites_data.images

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_domain.images.repository.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

class FavoritesImageRepositoryImpl @Inject constructor(
    private val imagesLocalDataSource: ImagesLocalDataSource,
) : FavoritesImageRepository {

    override fun getFavoritesImage(): Flow<List<Image>> =
        imagesLocalDataSource.getFavoritesImage().flowOn(Dispatchers.IO)

    override suspend fun insertImage(image: Image) =
        imagesLocalDataSource.insertImage(image)

    override suspend fun deleteImage(image: Image) =
        imagesLocalDataSource.deleteImage(image)

    override suspend fun deleteAllImage() = imagesLocalDataSource.deleteAllImage()
}

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesImageRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: FavoritesImageRepositoryImpl): FavoritesImageRepository
}