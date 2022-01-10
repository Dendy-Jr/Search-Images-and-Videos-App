package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

/**
 * @author Dendy-Jr on 04.01.2022
 * olehvynnytskyi@gmail.com
 */
class SearchVideoUseCaseTest {

    private val videoRepository = mock<VideosRepository>()

    @Test
    fun search_video_by_query_test(): Unit = runBlocking {

        val useCase = SearchVideoUseCase(repository = videoRepository)

        useCase.searchVideo("animal")

        Mockito.verify(videoRepository).searchVideo("animal")
    }
}