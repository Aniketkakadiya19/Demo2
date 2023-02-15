package com.example.demo2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    String title;
    String text;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {


        final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";

//        NotificationChannel channel = new NotificationChannel(
//                CHANNEL_ID,
//                "Heads Up Notification",
//                NotificationManager.IMPORTANCE_HIGH
//        );
//
//        Notification.Builder notification =
//                new Notification.Builder(this, CHANNEL_ID)
//                        .setContentTitle(title)
//                        .setContentText(text)
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setAutoCancel(true);
//
//        NotificationManagerCompat.from(this).notify(1, notification.build());

        super.onMessageReceived(remoteMessage);
        sendNotification();

        Log.w("TAGGG", "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d("TAGGG", "Message data payload: " + remoteMessage.getData());
        }

        }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification() {
        NotificationChannel channel = new NotificationChannel("notification","notification_name",
                NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"notification")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hey, Nasty user! There is falana dhinkana offer running in our campaign. " +
                                "please check out our products before it gets out of stock"));


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }

}
