package com.dendi.android.search_images_and_videos_app.di

import androidx.paging.ExperimentalPagingApi
import com.dendi.android.search_images_and_videos_app.data.image.cloud.DownloadWorker
import com.dendi.android.search_images_and_videos_app.presentation.favorite.FavoritesImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.favorite.FavoritesVideoViewModel
import com.dendi.android.search_images_and_videos_app.presentation.image.ImageDetailViewModel
import com.dendi.android.search_images_and_videos_app.presentation.image.ImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.video.VideoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */

@ExperimentalPagingApi
val appModule = module {

    worker { DownloadWorker(appContext = get(), params = get(), api = get()) }

    viewModel<ImageViewModel> {
        ImageViewModel(
            repository = get(),
            state = get(),
            internetConnection = get()
        )
    }

    viewModel<VideoViewModel> { VideoViewModel(repository = get(), state = get()) }

    viewModel<FavoritesImageViewModel> {
        FavoritesImageViewModel(
            imagesRepository = get(),
        )
    }

    viewModel<FavoritesVideoViewModel> { FavoritesVideoViewModel(videosRepository = get()) }

    viewModel<ImageDetailViewModel> { ImageDetailViewModel(state = get()) }
}