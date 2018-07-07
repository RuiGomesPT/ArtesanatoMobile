package com.project.art.artesanato20;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    ImageView img;
    TextView name, email, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getBaseContext());

        img = findViewById(R.id.imgProf);
        name = findViewById(R.id.nameProf);
        email = findViewById(R.id.emailProf);

        Picasso.get().load(account.getPhotoUrl()).into(img);
        name.setText(account.getDisplayName());
        email.setText(account.getEmail());
    }
}
