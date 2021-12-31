package com.dendi.android.search_images_and_videos_app.core

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.dendi.android.search_images_and_videos_app.BuildConfig
import com.dendi.android.search_images_and_videos_app.di.appModule
import com.dendi.android.search_images_and_videos_app.di.dataModule
import com.dendi.android.search_images_and_videos_app.di.networkPhotoModule
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
@ExperimentalPagingApi
@ExperimentalSerializationApi
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            workManagerFactory()
            modules(listOf(networkPhotoModule, appModule, dataModule))
        }
    }
}