package ui.dendi.finder.app.feature_favorites.domain

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.app.feature_images.domain.Image

interface FavoritesImageRepository {

    fun getFavoritesImage(): Flow<Result<List<Image>>>
    suspend fun insertImage(image: Image)
    suspend fun deleteImage(image: Image)
    suspend fun deleteAllImage()
}