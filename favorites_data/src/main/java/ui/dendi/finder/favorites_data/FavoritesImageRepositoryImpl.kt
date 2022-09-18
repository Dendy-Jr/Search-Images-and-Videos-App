package ui.dendi.finder.favorites_data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_domain.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

class FavoritesImageRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: ImageLocalDataSource,
) : FavoritesImageRepository {
    override fun getFavoritesImage(): Flow<Result<List<Image>>> =
        favoriteLocalDataSource.getFavoritesImage()

    override suspend fun insertImage(image: Image) =
        favoriteLocalDataSource.insertImage(image)

    override suspend fun deleteImage(image: Image) =
        favoriteLocalDataSource.deleteImage(image)

    override suspend fun deleteAllImage() = favoriteLocalDataSource.deleteAllImages()
}

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesImageRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: FavoritesImageRepositoryImpl): FavoritesImageRepository
}