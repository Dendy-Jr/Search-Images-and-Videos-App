package com.dendi.android.search_images_and_videos_app.di

import androidx.paging.ExperimentalPagingApi
import com.dendi.android.search_images_and_videos_app.data.image.mapper.ImageCacheToImageMapper
import com.dendi.android.search_images_and_videos_app.data.image.mapper.ImageCloudToImageMapper
import com.dendi.android.search_images_and_videos_app.data.image.repository.ImagesRepositoryImpl
import com.dendi.android.search_images_and_videos_app.data.video.mapper.*
import com.dendi.android.search_images_and_videos_app.data.video.repository.VideosRepositoryImpl
import com.dendi.android.search_images_and_videos_app.domain.image.mapper.ImageToImageCacheMapper
import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.*
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.*
import org.koin.dsl.module

/**
 * @author Dendy-Jr on 03.01.2022
 * olehvynnytskyi@gmail.com
 */

@ExperimentalPagingApi
val domainImageModule = module {

    single<ImagesRepository> {
        ImagesRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
            mapperImage = get(),
            mapperEntity = get(),
            mapperDtoToImage = get()
        )
    }

    factory { ImageCacheToImageMapper() }

    factory { ImageToImageCacheMapper() }

    factory { ImageCloudToImageMapper() }

    factory { ClearImagesUseCase(repository = get()) }

    factory { DeleteImageUseCase(repository = get()) }

    factory { GetImagesUseCase(repository = get()) }

    factory { InsertImagesUseCase(repository = get()) }

    factory { InsertImageUseCase(repository = get()) }

    factory { SearchImageUseCase(repository = get()) }
}