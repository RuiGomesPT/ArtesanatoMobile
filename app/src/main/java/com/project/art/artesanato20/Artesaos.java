package com.project.art.artesanato20;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.impl.ArtigoFirebaseManager;
import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Artigo;
import com.project.art.artesanato20.models.User;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Artesaos extends Fragment {
    RecyclerView rv;
    ArrayList<Artesao> ARTESAOS = new ArrayList<>();
    UserAdapter userAdapter;
    FloatingActionButton QRC;

    public Artesaos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_artesaos, container, false);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        QRC = rootView.findViewById(R.id.floatQRC);
        QRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QRS.class);
                startActivity(i);
            }
        });

        rv = rootView.findViewById(R.id.recViewArtesao);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);

        ARTESAOS = ArtesaoFirebaseManager.getInstance().getArtList();

        fillArtList(rootView);

        return rootView;
    }

    public void fillArtList(View view) {
        ARTESAOS = ArtesaoFirebaseManager.getInstance().getArtList();

        userAdapter = new UserAdapter(ARTESAOS);
        rv.setAdapter(userAdapter);
    }

}
