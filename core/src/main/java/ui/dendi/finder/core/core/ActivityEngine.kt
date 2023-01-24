package ui.dendi.finder.core.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ui.dendi.finder.core.core.base.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ActivityEngine"

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
                if (activity is BaseActivity) {
                    currentActivity = activity
                }
                log(message = "onActivityCreated", tag = TAG)
            }

            override fun onActivityStarted(activity: Activity) {
                log(message = "onActivityStarted", tag = TAG)
            }

            override fun onActivityResumed(activity: Activity) {
                log(message = "onActivityResumed", tag = TAG)
            }

            override fun onActivityPaused(activity: Activity) {
                log(message = "onActivityPaused", tag = TAG)
            }

            override fun onActivityStopped(activity: Activity) {
                log(message = "onActivityStopped", tag = TAG)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                log(message = "onActivitySaveInstanceState", tag = TAG)
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (activity === currentActivity) currentActivity = null
                log(message = "onActivityDestroyed", tag = TAG)
            }
        })
    }
}