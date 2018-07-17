package com.project.art.artesanato20;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class EventsActivity extends AppCompatActivity {

    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private static final String CHANNEL_ID = "123";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Artesanato", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager nm = getSystemService(NotificationManager.class);
        nm.createNotificationChannel(serviceChannel);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotif(View view) {
        System.out.println("teste");
        Intent serviceIntent = new Intent(this, NotService.class);
        serviceIntent.putExtra("key", "ola");

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 2);


        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("gato", "gato");
        //startService(intent);
        //Intent intent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);

        /*NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notifyId = 1;
        String channelId = "some_channel_id";

        Notification notification = new Notification.Builder(EventsActivity.this)
                .setContentTitle("gorila123")
                .setWhen(1531134720000L)
                .setContentText(Long.toString(System.currentTimeMillis()))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setChannelId(channelId)
                .build();

        notificationManager.notify(uniqueID, notification);*/
    }


}
