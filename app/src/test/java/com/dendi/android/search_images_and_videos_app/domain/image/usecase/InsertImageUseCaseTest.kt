package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache
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
class InsertImageUseCaseTest {

    private val imageRepository = mock<ImagesRepository>()

    private val image = ImageCache(
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        "",
        0,
        "",
        0,
        "",
        0,
        "",
        "",
        "",
        0,
        "",
        0,
        0,
        "",
        0,
        false
    )

    @Test
    fun save_image_to_favorites_test() = runBlocking {

        val useCase = InsertImageUseCase(repository = imageRepository)

        useCase.saveImageToFavorites(image)

        Mockito.verify(imageRepository).insertImage(image)
    }
}