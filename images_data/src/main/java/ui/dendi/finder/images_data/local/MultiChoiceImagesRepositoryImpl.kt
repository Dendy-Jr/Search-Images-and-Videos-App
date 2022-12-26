package ui.dendi.finder.images_data.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_domain.repository.MultiChoiceImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

class MultiChoiceImagesRepositoryImpl @Inject constructor(
    private val dao: MultiChoiceImagesDao,
) : MultiChoiceImagesRepository {

    override fun getMultiChoiceImages(): Flow<List<Image>> =
        dao.getMultiChoiceImages().map {
            it.map {
                it.toDomain()
            }
        }

    override suspend fun insertImages(list: List<Image>) {
        dao.insertMultiChoiceImages(list.toMultiChoiceImages())
    }

    override suspend fun deleteAllMultiChoiceImages() {
        dao.clearAll()
    }

    private fun List<Image>.toMultiChoiceImages(): List<MultiChoiceImage> =
        this.map {
            it.toMultiChoiceImage()
        }

    private fun Image.toMultiChoiceImage() = MultiChoiceImage(
        collections = collections,
        comments = comments,
        downloads = downloads,
        id = id,
        imageHeight = imageHeight,
        imageSize = imageSize,
        imageWidth = imageWidth,
        largeImageURL = largeImageURL,
        likes = likes,
        pageURL = pageURL,
        previewHeight = previewHeight,
        previewURL = previewURL,
        previewWidth = previewWidth,
        tags = tags,
        type = type,
        user = user,
        userImageURL = userImageURL,
        views = views,
        webFormatHeight = webFormatHeight,
        webFormatURL = webFormatURL,
        webFormatWidth = webFormatWidth,
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface MultiChoiceImagesModule {

    @Singleton
    @Binds
    fun binds(impl: MultiChoiceImagesRepositoryImpl): MultiChoiceImagesRepository
}