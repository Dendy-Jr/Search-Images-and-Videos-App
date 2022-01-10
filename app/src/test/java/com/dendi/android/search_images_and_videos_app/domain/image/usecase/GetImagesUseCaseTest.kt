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
class GetImagesUseCaseTest {

    private val imagesRepository = mock<ImagesRepository>()

    @Test
    fun get_favorites_images_test(): Unit = runBlocking {

        val useCase = GetImagesUseCase(repository = imagesRepository)

        useCase.getFavoritesImages()

        Mockito.verify(imagesRepository).getImages()
    }
}