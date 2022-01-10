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
class GetVideosUseCaseTest {

    private val videoRepository = mock<VideosRepository>()

    @Test
    fun get_favorites_videos_test(): Unit = runBlocking {

        val useCase = GetVideosUseCase(repository = videoRepository)

        useCase.getFavoritesVideos()

        Mockito.verify(videoRepository).getVideos()
    }
}