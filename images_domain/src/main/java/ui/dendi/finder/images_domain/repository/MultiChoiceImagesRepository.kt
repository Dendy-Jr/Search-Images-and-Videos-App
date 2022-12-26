package ui.dendi.finder.images_domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Image

interface MultiChoiceImagesRepository {

    fun getMultiChoiceImages(): Flow<List<Image>>

    suspend fun insertImages(list: List<Image>)

    suspend fun deleteAllMultiChoiceImages()
}