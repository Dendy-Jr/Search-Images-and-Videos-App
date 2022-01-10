package com.dendi.android.search_images_and_videos_app.di

import com.dendi.android.search_images_and_videos_app.data.video.mapper.*
import com.dendi.android.search_images_and_videos_app.data.video.repository.VideosRepositoryImpl
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.*
import org.koin.dsl.module

/**
 * @author Dendy-Jr on 09.01.2022
 * olehvynnytskyi@gmail.com
 */

val domainVideoModule = module {

    single<VideosRepository> {
        VideosRepositoryImpl(
            service = get(),
            localDataSource = get(),
            mapper = get()
        )
    }

    factory { VideoCloudToCacheMapper(mapper = get()) }

    factory {
        VideosStreamsCloudToCacheMapper(
            largeMapper = get(),
            mediumMapper = get(),
            smallMapper = get(),
            tinyMapper = get()
        )
    }

    factory { LargeCloudToCacheMapper() }

    factory { MediumCloudToCacheMapper() }

    factory { SmallCloudToCacheMapper() }

    factory { TinyCloudToCacheMapper() }

    factory { ClearVideosUseCase(repository = get()) }

    factory { DeleteVideoUseCase(repository = get()) }

    factory { GetPagingVideoUseCase(repository = get()) }

    factory { GetVideosUseCase(repository = get()) }

    factory { InsertVideosUseCase(repository = get()) }

    factory { InsertVideoUseCase(repository = get()) }

    factory { SearchVideoUseCase(repository = get()) }
}