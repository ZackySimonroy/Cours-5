package com.example.a533.cours5.Notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.a533.cours5.MainActivity;
import com.example.a533.cours5.Notification.model.ImportantMessageModel;
import com.example.a533.cours5.Notification.model.MessageModel;
import com.example.a533.cours5.R;

public class NotificationCreator {
    public static Notification createNotificationForMessage(Context context, MessageModel message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "42")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();

    }


    public static Notification createNotificationForImportantMessage(Context context, ImportantMessageModel message){
        Intent marqueeCommeLueIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,marqueeCommeLueIntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "42")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_launcher_background, "Marqué comme lue",pendingIntent);
        return builder.build();

    }
}
