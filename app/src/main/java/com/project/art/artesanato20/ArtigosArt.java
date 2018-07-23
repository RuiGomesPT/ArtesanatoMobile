package com.project.art.artesanato20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.impl.ArtigoFirebaseManager;
import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Artigo;

import java.util.ArrayList;

public class ArtigosArt extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<Artigo> ARTIGOS = new ArrayList<>();
    String id;
    ArtigoArtAdapter aaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artigos_art);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            id = extras.getString("id");
        }

        rv = findViewById(R.id.rvArtigos);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);

        ARTIGOS = ArtigoFirebaseManager.getInstance().getArtigosByCreator(id);
        fillArtList();

    }

    public void fillArtList() {

        aaAdapter = new ArtigoArtAdapter(ARTIGOS);
        rv.setAdapter(aaAdapter);
    }
}
