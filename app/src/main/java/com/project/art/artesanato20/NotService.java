package com.project.art.artesanato20;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import static com.project.art.artesanato20.NotificationService.uniqueID;

public class NotService extends Service {
    private static final String CHANNEL_ID = "123";
    @Override
    public void onCreate() {
        System.out.println("cao");
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        System.out.println("gato");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, '0', notificationIntent, 0);
        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("doge")
                .setContentText("456")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


}
