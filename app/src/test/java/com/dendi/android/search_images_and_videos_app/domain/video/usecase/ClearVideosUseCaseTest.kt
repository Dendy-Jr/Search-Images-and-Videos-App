package com.dendi.android.search_images_and_videos_app.domain.video.usecase


import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

/**
 * @author Dendy-Jr on 04.01.2022
 * olehvynnytskyi@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class ClearVideosUseCaseTest {

    private val videoRepository = mock<VideosRepository>()

    @Test
    fun clear_all_favorites_videos_test() = runBlocking {

        val useCase = ClearVideosUseCase(repository = videoRepository)

        useCase.clearAllFavorites()

        Mockito.verify(videoRepository).deleteAllVideos()
    }
}