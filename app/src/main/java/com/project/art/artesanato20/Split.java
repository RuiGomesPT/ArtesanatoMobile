package com.project.art.artesanato20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Split extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);



    }

    public void getProfile(View view) {
        Intent i = new Intent(Split.this,
                Profile.class);
        startActivity(i);
        finish();
    }

    public void getInfo(View view) {
        Intent i = new Intent(Split.this,
                MainActivity.class);
        startActivity(i);
        finish();
    }

    public void getEvents(View view) {
        Intent i = new Intent(Split.this,
                EventsActivity.class);
        startActivity(i);
        finish();
    }
}
