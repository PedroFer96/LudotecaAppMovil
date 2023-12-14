package com.pedrofernandez.appmovilludoteca.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;


public class MapaLudotecaFragment extends Fragment implements OnMapReadyCallback {

    Ludoteca ludoteca;
    private GoogleMap mMap;
    private double latitud;
    private double longitud;

    public MapaLudotecaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_mapa_ludoteca, container, false);
        Bundle args = getArguments();
        ludoteca = (Ludoteca) args.getSerializable("ludoteca");

        latitud = ludoteca.getLatitud();
        longitud = ludoteca.getLongitud();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapaLudoteca);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //debemos añadirle el marcador personalizado
        LatLng posicionludoteca = new LatLng(latitud, longitud);

        mMap.addMarker(new MarkerOptions().position(posicionludoteca));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionludoteca, 15));
    }
}