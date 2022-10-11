package ui.dendi.finder.core.core.managers

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import ui.dendi.finder.core.R
import ui.dendi.finder.core.core.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    private val application: Application
) {

    //TODO This is no longer necessary

    fun show(
        channelId: String,
        channelName: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
        notificationManager: NotificationManager
    ) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            importance,
        )
        channel.enableVibration(true)
        channel.enableLights(true)
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder =
            NotificationCompat.Builder(application, channelId)

        notificationBuilder
            .setContentTitle(Constants.CHANNEL_DESC)
            .setProgress(100, 0, true)
            .setSmallIcon(R.drawable.ic_finder)
        notificationManager.notify(Constants.NOTIFICATION_ID, notificationBuilder.build())
    }
}