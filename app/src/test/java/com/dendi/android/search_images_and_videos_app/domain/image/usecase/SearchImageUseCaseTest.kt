package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

/**
 * @author Dendy-Jr on 03.01.2022
 * olehvynnytskyi@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class SearchImageUseCaseTest {

    private val imageRepository = mock<ImagesRepository>()

    @Test
    fun search_image_by_query_test(): Unit = runBlocking {

        val useCase = SearchImageUseCase(repository = imageRepository)

        useCase.searchImage("cars")

        Mockito.verify(imageRepository).searchImage("cars")
    }
}