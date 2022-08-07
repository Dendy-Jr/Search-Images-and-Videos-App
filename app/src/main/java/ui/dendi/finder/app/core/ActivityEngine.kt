package ui.dendi.finder.app.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ui.dendi.finder.app.activity.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityEngine @Inject constructor(
    application: Application,
) : Logger by LoggerImpl() {

    var currentActivity: AppCompatActivity? = null
        private set

    init {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is MainActivity) {
                    currentActivity = activity
                }
                log("onActivityCreated")
            }

            override fun onActivityStarted(activity: Activity) {
                log("onActivityStarted")
            }

            override fun onActivityResumed(activity: Activity) {
                log("onActivityResumed")
            }

            override fun onActivityPaused(activity: Activity) {
                log("onActivityPaused")
            }

            override fun onActivityStopped(activity: Activity) {
                log("onActivityStopped")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                log("onActivitySaveInstanceState")
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (activity === currentActivity) currentActivity = null
                log("onActivityDestroyed")
            }
        })
    }
}