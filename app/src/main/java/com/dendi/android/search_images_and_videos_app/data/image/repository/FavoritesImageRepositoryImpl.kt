package com.dendi.android.search_images_and_videos_app.data.image.repository

import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.image.cache.FavoriteImage
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.repository.FavoritesImageRepository
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
    override fun getFavoritesImage(): Flow<List<Image>> =
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