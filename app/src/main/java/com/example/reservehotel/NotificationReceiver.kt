package com.example.reservehotel

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDeepLinkBuilder
import com.example.reservehotel.InputFragment.Companion.CHANNEL_ID
import com.example.reservehotel.InputFragment.Companion.getNotificationUniqueID

class NotificationReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val myIntent = Intent(context, DisplayFragment::class.java)
        /*val pendingIntent =
            PendingIntent.getActivity(
                context,
                getNotificationUniqueID(),
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )*/

        val pendingIntent = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.my_nav)
            .setDestination(R.id.displayFragment)
            .createPendingIntent()

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Hotel Reservation Reminder")
            .setContentText("You have a hotel reservation coming up!")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)    // notification gets deleted after clicking on it

        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(getNotificationUniqueID(), builder.build())

        Log.i("action", "notification posted")
    }
}