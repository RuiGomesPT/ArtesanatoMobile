package com.project.art.artesanato20;

import android.app.Notification;
import android.app.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.project.art.artesanato20.impl.EventFirebaseManager;
import com.project.art.artesanato20.models.Event;

public class NotReceiver extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            Event EVENT = EventFirebaseManager.getInstance().getEventById(id);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(EVENT.getNome())
                    .setContentText("O evento " + EVENT.getNome() + "está mesmo a começar")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId("Artesanato Evento")
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        }

    }
}
