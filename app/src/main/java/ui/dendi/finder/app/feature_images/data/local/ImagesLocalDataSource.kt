package ui.dendi.finder.app.feature_images.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.app.feature_images.domain.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesLocalDataSource @Inject constructor(
    private val imageDao: ImageDao,
) {

    fun getImages(): Flow<List<Image>> =
        imageDao.getImages().map { list ->
            list.map { it.toDomain() }
        }

    suspend fun insertAllImages(images: List<Image>) =
        imageDao.insertAll(images.map { it.toCache() })

    suspend fun insertImage(image: Image) =
        imageDao.insertImage(image.toCache())

    suspend fun deleteImage(image: Image) =
        imageDao.deleteImage(image.toCache())

    suspend fun deleteAllImages() =
        imageDao.clearAll()
}