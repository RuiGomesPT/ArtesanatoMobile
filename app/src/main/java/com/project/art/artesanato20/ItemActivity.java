package com.project.art.artesanato20;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.impl.ArtigoFirebaseManager;
import com.project.art.artesanato20.models.Artesao;
import com.project.art.artesanato20.models.Artigo;
import com.project.art.artesanato20.models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;

public class ItemActivity extends AppCompatActivity {
    private GoogleMap mMap;
    LatLng latlng;
    Bitmap bmp;
    Artigo artigo;
    Artesao artesao;
    ImageView img, imgArt;
    TextView name, type, nameArt;
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

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_item);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.build();
        ImageLoader.getInstance().init(config);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            artigo = ArtigoFirebaseManager.getInstance().getArtigoById(id);
            artesao = ArtesaoFirebaseManager.getInstance().getArtesaoById(artigo.getId_creator());
            latlng = new LatLng(artesao.getxCoor(), artesao.getyCoor());
        }

        name = findViewById(R.id.nameTextItem);
        nameArt = findViewById(R.id.textArtItem);
        type = findViewById(R.id.typeTextItem);
        img = findViewById(R.id.imageTextItem);
        imgArt = findViewById(R.id.imgTextArtItem);

        name.setText(artigo.getNome());
        nameArt.setText(artesao.getName());
        type.setText(artigo.getTipo());

        imageLoader = ImageLoader.getInstance();



        //bmp = imageLoader.loadImageSync(artigo.getPhotoURL());

        System.out.println(artesao.getPhotoURL());
        //imageLoader.displayImage(artigo.getPhotoURL(), img);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference artImg = mStorageRef.child("images").child(artigo.getPhotoURL());

        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File newFile = localFile;
        artImg.getFile(newFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...

                        img.setImageURI(Uri.fromFile(newFile));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }


        });
        //Picasso.get().load(artigo.getPhotoURL()).fit().into(img);
        Picasso.get().load(artesao.getPhotoURL()).fit().into(imgArt);

        ITEM = new CameraPosition.Builder().target(latlng)
                        .zoom(DEFAULT_ZOOM)
                        .bearing(300)
                        .tilt(90)
                        .build();




        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapaItem);
        getLocationPermission();


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
        imageLoader.loadImage(artigo.getPhotoURL(), new SimpleImageLoadingListener() {
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

        System.out.println(downloadMarker());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .snippet(artesao.getId())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(artesao.getName()));

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
