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
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.project.art.artesanato20.impl.ArtigoFirebaseManager;
import com.project.art.artesanato20.impl.EventFirebaseManager;
import com.project.art.artesanato20.models.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class EventsActivity extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Event> EVENTS = new ArrayList<>();
    EventAdapter eventAdapter;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private static final String CHANNEL_ID = "123";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Artesanato", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(serviceChannel);
        }



        rv = findViewById(R.id.recViewEvents);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());
        rv.setLayoutManager(llm);

        EVENTS = EventFirebaseManager.getInstance().getEventList();

        fillEventList();

    }

    public void fillEventList() {
        EVENTS = EventFirebaseManager.getInstance().getEventList();
        eventAdapter = new EventAdapter(EVENTS);
        rv.setAdapter(eventAdapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent i = new Intent(EventsActivity.this, Split.class);
            this.startActivity(i);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
