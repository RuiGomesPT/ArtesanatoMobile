package com.project.art.artesanato20;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {
    ImageView img;
    TextView name, type, email;

    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        img = rootView.findViewById(R.id.imgProfile);
        name = rootView.findViewById(R.id.nameProfile);
        type = rootView.findViewById(R.id.typeProfile);
        email = rootView.findViewById(R.id.emailProfile);

        name.setText(account.getDisplayName());
        email.setText(account.getEmail());
        Picasso.get().load(account.getPhotoUrl()).into(img);
        return rootView;


    }


}
