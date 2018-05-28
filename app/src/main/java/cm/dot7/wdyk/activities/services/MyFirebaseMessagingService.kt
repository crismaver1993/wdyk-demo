package cm.dot7.wdyk.activities.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import cm.dot7.wdyk.R
import cm.dot7.wdyk.activities.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by cmarin on 5/24/2018
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        // Check if message contains a data payload.
        if (remoteMessage!!.data.size > 0) {
            remoteMessage.data["title"]?.let { remoteMessage.data["author"]?.let { it1 -> showNotification(it, it1) } }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {

        }
    }

    private fun showNotification(title: String, author: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setContentTitle("New Article: $title")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("By $author")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}