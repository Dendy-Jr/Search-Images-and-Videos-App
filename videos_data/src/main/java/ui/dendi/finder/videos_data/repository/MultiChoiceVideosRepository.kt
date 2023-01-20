package ui.dendi.finder.videos_data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ui.dendi.finder.core.core.models.*
import ui.dendi.finder.videos_data.local.*
import ui.dendi.finder.videos_domain.repository.MultiChoiceVideosRepository
import javax.inject.Inject
import javax.inject.Singleton

class MultiChoiceVideosRepositoryImpl @Inject constructor(
    private val dao: MultiChoiceVideosDao,
) : MultiChoiceVideosRepository {

    override fun getMultiChoiceVideos(): Flow<List<Video>> =
        dao.getMultiChoiceVideos().map {
            it.map {
                it.toDomain()
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun insertVideos(list: List<Video>) {
        dao.insertMultiChoiceVideos(list.toMultiChoiceVideos())
    }

    override suspend fun deleteAllMultiChoiceVideos() {
        dao.clearAll()
    }

    private fun List<Video>.toMultiChoiceVideos(): List<MultiChoiceVideo> =
        this.map { it.toMultiChoiceVideo() }

    private fun Video.toMultiChoiceVideo(): MultiChoiceVideo = MultiChoiceVideo(
        id = id,
        comments = comments,
        date = date,
        downloads = downloads,
        duration = duration,
        likes = likes,
        pageURL = pageURL,
        pictureId = pictureId,
        tags = tags,
        type = type,
        user = user,
        userId = userId,
        userImageURL = userImageURL,
        videosStreams = videos.toMultiChoiceVideosStreams(),
        views = 0,
    )

    private fun VideosStreams.toMultiChoiceVideosStreams(): MultiChoiceVideosStreams =
        MultiChoiceVideosStreams(
            large = large.toMultiChoiceLarge(),
            medium = medium.toMultiChoiceMedium(),
            small = small.toMultiChoiceSmall(),
            tiny = tiny.toMultiChoiceTiny(),
        )

    private fun Large.toMultiChoiceLarge(): MultiChoiceLarge =
        MultiChoiceLarge(height = height, size = size, url = url, width = width)

    private fun Medium.toMultiChoiceMedium(): MultiChoiceMedium =
        MultiChoiceMedium(height = height, size = size, url = url, width = width)

    private fun Small.toMultiChoiceSmall(): MultiChoiceSmall =
        MultiChoiceSmall(height = height, size = size, url = url, width = width)

    private fun Tiny.toMultiChoiceTiny(): MultiChoiceTiny =
        MultiChoiceTiny(height = height, size = size, url = url, width = width)
}

@Module
@InstallIn(SingletonComponent::class)
interface MultiChoiceVideosRepositoryModule {

    @Singleton
    @Binds
    fun binds(impl: MultiChoiceVideosRepositoryImpl): MultiChoiceVideosRepository
}