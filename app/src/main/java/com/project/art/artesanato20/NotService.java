package com.project.art.artesanato20;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.project.art.artesanato20.impl.EventFirebaseManager;
import com.project.art.artesanato20.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class NotService extends Service {
    private static final String CHANNEL_ID = "123";
    public ArrayList<Event> EVENTS = new ArrayList<>();

    @Override
    public void onCreate() {
        System.out.println("cao");
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            Event EVENT = EventFirebaseManager.getInstance().getEventById(id);
            EVENTS.add(EVENT);

        }

        for (int i = 0; i < EVENTS.size(); i++) {
            //String dateString = EVENTS.get(i).getData() + " " + EVENTS.get(i).getHora();
            String dateString = "17/07/2018 19:14";

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date mDate = sdf.parse(dateString);
                long timeInMilliseconds = mDate.getTime();
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent myIntent;
                PendingIntent pendingIntent;
                myIntent = new Intent(NotService.this, NotReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
                System.out.println(timeInMilliseconds);
                System.out.println(System.currentTimeMillis());
                am.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, pendingIntent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

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
