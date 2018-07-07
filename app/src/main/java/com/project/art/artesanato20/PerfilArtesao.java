package com.project.art.artesanato20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.models.Artesao;
import com.squareup.picasso.Picasso;

public class PerfilArtesao extends AppCompatActivity {
    private GoogleMap mMap;
    LatLng latlng;
    Bitmap bmp;
    Artesao ARTESAO;
    TextView nome, atv, email;
    ImageView img;
    ImageLoader imageLoader;
    SupportMapFragment mapFragment;
    private StorageReference mStorageRef;
    private static final float DEFAULT_ZOOM = 18f;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public static CameraPosition ITEM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_artesao);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String artId = extras.getString("id");
            Artesao artesao = ArtesaoFirebaseManager.getInstance().getArtesaoById(artId);
            ARTESAO = artesao;
            latlng = new LatLng(artesao.getxCoor(), artesao.getyCoor());
            fillData();
        }

        ITEM = new CameraPosition.Builder().target(latlng)
                .zoom(DEFAULT_ZOOM)
                .bearing(300)
                .tilt(90)
                .build();




        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaArtesao);
        getLocationPermission();
    }

    public void fillData() {
        nome = findViewById(R.id.namePrefArt);
        nome.setText(ARTESAO.getName());

        email = findViewById(R.id.emailProf);
        email.setText(ARTESAO.getEmail());

        atv = findViewById(R.id.artPrefArt);
        atv.setText(ARTESAO.getNomeAtv());

        img = findViewById(R.id.imgPerfArt);
        Picasso.get().load(ARTESAO.getPhotoURL()).fit().into(img);
    }

    private void getLocationPermission() {
        String[] permissions = {
                FINE_LOCATION,
                COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(
                this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);

            }
        }
        else {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);

        }
    }

    private Bitmap downloadMarker() {
        imageLoader.loadImage(ARTESAO.getPhotoURL(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                bmp = Bitmap.createBitmap(80, 80, conf);
                Canvas canvas1 = new Canvas(bmp);

                Paint color = new Paint();
                color.setTextSize(35);
                color.setColor(Color.BLACK);


                canvas1.drawBitmap(loadedImage, 0,0, color);
                canvas1.drawText("User Name!", 30, 40, color);
                //img.setImageBitmap(loadedImage);


                //setMarker();
            }
        });
        return bmp;
    }

    private void setMarker() {

        //System.out.println(downloadMarker());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .snippet(ARTESAO.getId())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(ARTESAO.getName()));

    }

    private void initMap() {

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                if (mLocationPermissionGranted) {
                    getDeviceLocation();

                    if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;

                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(ITEM));
                    //downloadMarker();
                    setMarker();

                }

            }
        });

    }
    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        if(currentLocation != null) {
                            latlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
                        //populateMap();
                    }
                }
            });

        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }
}
