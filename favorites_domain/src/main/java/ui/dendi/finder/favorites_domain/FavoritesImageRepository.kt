package ui.dendi.finder.favorites_domain

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Image

interface FavoritesImageRepository {

    fun getFavoritesImage(): Flow<Result<List<Image>>>
    suspend fun insertImage(image: Image)
    suspend fun deleteImage(image: Image)
    suspend fun deleteAllImage()
}