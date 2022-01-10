package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Dendy-Jr on 03.01.2022
 * olehvynnytskyi@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class DeleteImageUseCaseTest {

    private val imageRepository = mock<ImagesRepository>()

    private val image = Image(
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
        "",
        0,
        0,
        "",
        0,
    )

    @Test
    fun delete_favorite_image_test() = runBlocking {

        val useCase = DeleteImageUseCase(repository = imageRepository)

        useCase.deleteFavorite(image)

        verify(imageRepository).deleteImage(image)
    }
}