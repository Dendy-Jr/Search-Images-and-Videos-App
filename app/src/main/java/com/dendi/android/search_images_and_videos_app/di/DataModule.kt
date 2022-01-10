package com.dendi.android.search_images_and_videos_app.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.dendi.android.search_images_and_videos_app.data.core.PixabayDb
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeysDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesRemoteDataSource
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideosLocalDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * @author Dendy-Jr on 13.12.2021
 * olehvynnytskyi@gmail.com
 */
private const val DATABASE_NAME = "pixabay_db"


val dataModule = module {

    single<SavedStateHandle> { SavedStateHandle() }

    fun getPixabayDatabase(application: Application): PixabayDb {
        return Room.databaseBuilder(
            application,
            PixabayDb::class.java,
            DATABASE_NAME
        ).build()
    }

    factory<PixabayDb> { getPixabayDatabase(application = androidApplication()) }

    fun getImageDao(pixabayDb: PixabayDb): ImageDao {
        return pixabayDb.imageDao()
    }

    fun getVideoDao(pixabayDb: PixabayDb): VideoDao {
        return pixabayDb.videoDao()
    }

    fun getImageRemoteKeysDao(pixabayDb: PixabayDb): ImageRemoteKeysDao {
        return pixabayDb.imageRemoteKeysDao()
    }

    factory<ImageDao> { getImageDao(pixabayDb = get()) }

    factory<VideoDao> { getVideoDao(pixabayDb = get()) }

    factory<ImageRemoteKeysDao> { getImageRemoteKeysDao(pixabayDb = get()) }

    factory<ImagesLocalDataSource> { ImagesLocalDataSource.ImagesLocalDataSourceImpl(imageDao = get()) }

    factory<ImagesRemoteDataSource> { ImagesRemoteDataSource.ImagesRemoteDataSourceImpl(imagesApi = get()) }

    factory<VideosLocalDataSource> { VideosLocalDataSource.VideosLocalDataSourceImpl(videoDao = get()) }
}