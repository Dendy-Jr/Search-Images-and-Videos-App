package ui.dendi.finder.favorites_domain.images.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Image

interface FavoritesImageRepository {

    fun getFavoritesImage(): Flow<List<Image>>

    suspend fun insertImage(image: Image)

    suspend fun deleteImage(image: Image)
}