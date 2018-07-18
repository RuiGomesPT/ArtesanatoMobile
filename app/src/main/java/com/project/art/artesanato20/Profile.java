package com.project.art.artesanato20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
            Intent i = new Intent(Profile.this, Split.class);
            this.startActivity(i);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
