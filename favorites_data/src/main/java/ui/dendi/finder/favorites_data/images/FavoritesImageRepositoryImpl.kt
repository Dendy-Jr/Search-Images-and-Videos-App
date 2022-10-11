package ui.dendi.finder.favorites_data.images

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_domain.images.repository.FavoritesImageRepository
import ui.dendi.finder.images_data.local.ImageEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class FavoritesImageRepositoryImpl @Inject constructor(
    private val dao: FavoritesImageDao,
) : FavoritesImageRepository {

    override fun getFavoritesImage(): Flow<Result<List<Image>>> =
        dao.getFavoritesImage().map { images ->
            Result.success(images.map {
                it.image.toDomain()
            })
        }

    override suspend fun insertImage(image: Image) =
        dao.insertImage(image.toFavorite())

    override suspend fun deleteImage(image: Image) =
        dao.deleteImage(image.toFavorite())

    override suspend fun deleteAllImage() = dao.clearAll()

    private fun Image.toFavorite() = FavoriteImage(
        id = id,
        image = ImageEntity(
            collections = collections,
            comments = comments,
            downloads = downloads,
            id = id,
            localId = id,
            imageHeight = imageHeight,
            imageSize = imageSize,
            imageWidth = imageWidth,
            largeImageURL = largeImageURL,
            likes = likes,
            pageURL = pageURL,
            previewWidth = previewWidth,
            previewHeight = previewHeight,
            previewURL = previewURL,
            tags = tags,
            type = type,
            user = user,
            userImageURL = userImageURL,
            views = views,
            webFormatHeight = webFormatHeight,
            webFormatURL = webFormatURL,
            webFormatWidth = webFormatWidth,
            date = Calendar.getInstance().time,
        )
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface FavoritesImageRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: FavoritesImageRepositoryImpl): FavoritesImageRepository
}