package com.dendi.android.search_images_and_videos_app.feature_favorites.data

import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_favorites.domain.FavoritesImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
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