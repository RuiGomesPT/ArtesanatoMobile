package com.project.art.artesanato20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.impl.ArtigoFirebaseManager;
import com.project.art.artesanato20.impl.EventFirebaseManager;
import com.project.art.artesanato20.impl.UserFirebaseManager;
import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Artigo;
import com.project.art.artesanato20.models.Event;
import com.project.art.artesanato20.models.User;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {
    public ArrayList<User> USERS;
    public ArrayList<Artigo> ITEMS;
    public ArrayList<Event> EVENTS;
    public boolean init = true;
    int z = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_loading);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        FirebaseApp.initializeApp(this);
        loadUsers();
        loadItems();
        loadEvents();

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    Intent i = new Intent(LoadingActivity.this,
                            Split.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }

    public void loadUsers() {
        USERS = UserFirebaseManager.getInstance().getUserList();
        UserFirebaseManager.getInstance().clearUserList();
        ArtesaoFirebaseManager.getInstance().clearArtList();


        DatabaseReference dataUsers;
        dataUsers = FirebaseDatabase.getInstance().getReference().child("users");
        dataUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String tipo = ds.child("type").getValue(String.class);
                    String id = ds.child("uid").getValue(String.class);
                    String nome = ds.child("displayName").getValue(String.class);
                    String photoID = ds.child("photoURL").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    if (tipo.equals("2")) {
                        String codAtv = ds.child("codAtv").getValue(String.class);
                        String nomeAtv = ds.child("nomeAtv").getValue(String.class);
                        Double xCoor = ds.child("xCoor").getValue(Double.class);
                        Double yCoor = ds.child("yCoor").getValue(Double.class);
                        if (init) {
                            Artesao art = new Artesao(id, nome, photoID, email, tipo, nomeAtv, codAtv, xCoor, yCoor);
                            artToList(art);
                            init = false;
                        } else {
                            boolean exists = false;
                            ArrayList<Artesao> ARTESAOF = ArtesaoFirebaseManager.getInstance().getArtList();
                            for (int i = 0; i < ARTESAOF.size(); i++) {
                                if (ARTESAOF.get(i).getId().equals(id)) {
                                    exists = true;
                                    z = i;
                                }
                            }
                            if (!exists) {
                                Artesao art = new Artesao(id, nome, photoID, email, tipo, nomeAtv, codAtv,  xCoor, yCoor);
                                artToList(art);
                            } else {
                                Artesao art = new Artesao(id, nome, photoID, email, tipo, nomeAtv, codAtv,  xCoor, yCoor);
                                ArtesaoFirebaseManager.getInstance().getArtList().set(z, art);
                            }
                        }
                    } else {
                        if (init) {
                            User user = new User(id, nome, email, photoID, tipo);
                            userToList(user);
                            init = false;
                        } else {
                            boolean exists = false;
                            ArrayList<User> USERSF = UserFirebaseManager.getInstance().getUserList();
                            for (int i = 0; i < USERSF.size(); i++) {
                                if (USERSF.get(i).getId().equals(id)) {
                                    exists = true;
                                    z = i;
                                }
                            }
                            if (!exists) {
                                User user = new User(id, nome, email, photoID, tipo);
                                userToList(user);
                            } else {
                                User user = new User(id, nome, email, photoID, tipo);
                                UserFirebaseManager.getInstance().getUserList().set(z, user);
                            }
                        }
                    }





                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.toString());
            }


        });


    }

    public void loadItems() {

        ITEMS = ArtigoFirebaseManager.getInstance().getItemList();
        ArtigoFirebaseManager.getInstance().clearList();

        DatabaseReference dataItems;
        dataItems = FirebaseDatabase.getInstance().getReference().child("artigos");
        dataItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String nome = ds.child("name").getValue(String.class);
                    String nomeCriador = ds.child("nameCreator").getValue(String.class);
                    String photoURL = ds.child("photoURL").getValue(String.class);
                    String uid = ds.child("uid").getValue(String.class);
                    ArrayList<String> likesList = new ArrayList<>();

                    for (DataSnapshot likesShot: ds.child("likesList").getChildren()) {
                        likesList.add(likesShot.getValue().toString());
                    }

                    if (init) {
                        Artigo artigo = new Artigo(id, nome, uid, nomeCriador, photoURL, likesList);
                        itemToList(artigo);
                        init = false;
                    } else {
                        boolean exists = false;
                        ArrayList<Artigo> ITEMSF = ArtigoFirebaseManager.getInstance().getItemList();
                        for (int i = 0; i < ITEMSF.size(); i++) {
                            if (ITEMSF.get(i).getId().equals(id)) {
                                exists = true;
                                z = i;
                            }
                        }
                        if (!exists) {
                            Artigo artigo = new Artigo(id, nome, uid, nomeCriador, photoURL, likesList);
                            itemToList(artigo);
                        } else {
                            Artigo artigo = new Artigo(id, nome, uid, nomeCriador, photoURL, likesList);
                            ArtigoFirebaseManager.getInstance().getItemList().set(z, artigo);
                        }
                    }




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.toString());
            }


        });
    }

    public void loadEvents() {
        EVENTS = EventFirebaseManager.getInstance().getEventList();
        EventFirebaseManager.getInstance().clearList();

        DatabaseReference dataItems;
        dataItems = FirebaseDatabase.getInstance().getReference().child("events");
        dataItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String nome = ds.child("nome").getValue(String.class);
                    String data = ds.child("data").getValue(String.class);
                    String hora = ds.child("hora").getValue(String.class);
                    String info = ds.child("info").getValue(String.class);
                    String imgURL = ds.child("imgURL").getValue(String.class);


                    if (init) {
                        Event event = new Event(id, nome, info, data, hora, imgURL);
                        eventToList(event);
                        init = false;
                    } else {
                        boolean exists = false;
                        ArrayList<Event> EVENTSF = EventFirebaseManager.getInstance().getEventList();
                        for (int i = 0; i < EVENTSF.size(); i++) {
                            if (EVENTSF.get(i).getId().equals(id)) {
                                exists = true;
                                z = i;
                            }
                        }
                        if (!exists) {
                            Event event = new Event(id, nome, info, data, hora, imgURL);
                            eventToList(event);
                        } else {
                            Event event = new Event(id, nome, info, data, hora, imgURL);
                            EventFirebaseManager.getInstance().getEventList().set(z, event);
                        }
                    }




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.toString());
            }


        });
    }

    public void userToList(User user) {
        UserFirebaseManager.getInstance().addUserToList(user);
    }

    public void itemToList(Artigo artigo) {ArtigoFirebaseManager.getInstance().addItemToList(artigo);}

    public void artToList(Artesao art) {ArtesaoFirebaseManager.getInstance().addArtToList(art);}

    public void eventToList(Event event) {EventFirebaseManager.getInstance().addEventToList(event);}
}

