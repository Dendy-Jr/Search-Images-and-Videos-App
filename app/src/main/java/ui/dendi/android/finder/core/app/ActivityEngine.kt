package ui.dendi.android.finder.core.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import ui.dendi.android.finder.app.activity.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityEngine @Inject constructor(
    application: Application,
) {

    var currentActivity: AppCompatActivity? = null
        private set

    init {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is MainActivity) {
                    currentActivity = activity
                }
                Timber.d("onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                Timber.d("onActivityStarted")
            }

            override fun onActivityResumed(activity: Activity) {
                Timber.d("onActivityResumed")
            }

            override fun onActivityPaused(activity: Activity) {
                Timber.d("onActivityPaused")
            }

            override fun onActivityStopped(activity: Activity) {
                Timber.d("onActivityStopped")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Timber.d("onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (activity === currentActivity) currentActivity = null
                Timber.d("onActivityDestroyed")
            }
        })
    }
}