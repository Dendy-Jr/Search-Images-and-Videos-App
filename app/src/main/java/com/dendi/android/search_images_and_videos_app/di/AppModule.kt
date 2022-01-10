package com.dendi.android.search_images_and_videos_app.di

import androidx.paging.ExperimentalPagingApi
import com.dendi.android.search_images_and_videos_app.data.core.DownloadWorker
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesVideoViewModel
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.ImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.VideoViewModel
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
            searchImageUseCase = get(),
            insertImageUseCase = get(),
            state = get()
        )
    }

    viewModel<VideoViewModel> {
        VideoViewModel(
            searchVideoUseCase = get(),
            insertVideoUseCase = get(),
            state = get()
        )
    }

    viewModel<FavoritesImageViewModel> {
        FavoritesImageViewModel(
            getImagesUseCase = get(),
            deleteImageUseCase = get(),
            clearImagesUseCase = get()
        )
    }

    viewModel<FavoritesVideoViewModel> {
        FavoritesVideoViewModel(
            getVideosUseCase = get(),
            deleteVideoUseCase = get(),
            clearVideosUseCase = get()
        )
    }
}