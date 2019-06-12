package com.project.art.artesanato20;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.project.art.artesanato20.impl.ArtesaoFirebaseManager;
import com.project.art.artesanato20.models.Artesao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mapa extends Fragment implements GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    LatLng latlng;
    private static final float DEFAULT_ZOOM = 18.5f;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ArrayList<Marker> markerList = new ArrayList<>();
    public TextView nome, tipo;
    public ImageView imagem;
    public FloatingActionButton fab;
    public String idArt;

    public static final CameraPosition FEIRA =
            new CameraPosition.Builder().target(new LatLng(41.352133,-8.7485))
                    .zoom(DEFAULT_ZOOM)
                    .bearing(200)
                    .tilt(45)
                    .build();

    public Mapa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        nome = rootView.findViewById(R.id.nomeArtesaoMapa);
        tipo = rootView.findViewById(R.id.tipoArtesaoMapa);
        imagem = rootView.findViewById(R.id.imagemArtesaoMapa);
        fab = rootView.findViewById(R.id.fabSearch);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idArt != null) {
                    Intent i = new Intent(getContext(), PerfilArtesao.class);
                    i.putExtra("id", idArt);
                    startActivity(i);
                }
            }
        });


        getLocationPermission();
        return rootView;
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        try {
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Location currentLocation = (Location) task.getResult();
                        if (currentLocation != null) {
                            latlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        }
                        if (latlng != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, DEFAULT_ZOOM));
                        }

                        populateMap();
                    }
                }
            });

        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        for (int i = 0; i < markerList.size(); i++) {
            if (marker.getId().equals(markerList.get(i).getId())) {
                Artesao ARTESAO = ArtesaoFirebaseManager.getInstance().getArtesaoById(marker.getSnippet());
                nome.setText(ARTESAO.getName());
                tipo.setText(ARTESAO.getNomeAtv());
                Picasso.get().load(ARTESAO.getPhotoURL()).fit().into(imagem);
                idArt = marker.getSnippet();
            }
        }

        return true;
    }

    private void getLocationPermission() {
        String[] permissions = {
                FINE_LOCATION,
                COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(
                this.getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this.getActivity().getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(
                        this.getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);

            }
        }
        else {
            ActivityCompat.requestPermissions(
                    this.getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);

        }
    }
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFeira);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                if (mLocationPermissionGranted) {
                    getDeviceLocation();

                    if (ActivityCompat.checkSelfPermission(getActivity().getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getBaseContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;

                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    LatLng feira = new LatLng(41.352133,-8.7485);
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(FEIRA));
                    //markerClick();

                }

            }
        });

    }

    private void populateMap() {
        ArrayList<Artesao> ART = ArtesaoFirebaseManager.getInstance().getArtList();

        for (int i = 0; i < ART.size(); i++) {
            LatLng lnglat = new LatLng(ART.get(i).getxCoor(), ART.get(i).getyCoor());
            setMarker(lnglat, ART.get(i));
        }

    }



    private void setMarker(LatLng latlng, final Artesao artesao) {

        mMap.setOnMarkerClickListener(this);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .snippet(artesao.getId())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(artesao.getName()));

        markerList.add(marker);


    }
}
