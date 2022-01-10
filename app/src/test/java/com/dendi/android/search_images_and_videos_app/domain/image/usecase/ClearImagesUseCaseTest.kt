package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import kotlinx.coroutines.runBlocking
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
class ClearImagesUseCaseTest {

    private val imagesRepository = mock<ImagesRepository>()

    @Test
    fun clear_all_favorites_images_test() = runBlocking {

        val useCase = ClearImagesUseCase(repository = imagesRepository)

        useCase.clearAllFavorites()

        Mockito.verify(imagesRepository).deleteAllImages()
    }
}