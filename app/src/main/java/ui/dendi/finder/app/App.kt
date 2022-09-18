package ui.dendi.finder.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ui.dendi.finder.core.core.ActivityEngine
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