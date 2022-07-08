package com.dendi.android.search_images_and_videos_app.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var activityEngine: ActivityEngine

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}