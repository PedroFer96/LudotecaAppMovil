package com.pedrofernandez.appmovilludoteca.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.activities.LudotecaActivity;
import com.pedrofernandez.appmovilludoteca.adapter.CustomInfoWindow;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.google.android.gms.location.FusedLocationProviderClient;


public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private ArrayList<Ludoteca> listaLudotecas;
    private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        listaLudotecas = new ArrayList<>();
        cargarLudotecas();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void cargarLudotecas() {

        try{
            SocketHandler.getOut().println(Mensajes.PETICION_MAPA_LUDOTECAS_MOVIL);

            String received ;
            String flag="";
            String [] args;

            received = SocketHandler.getIn().readLine();
            args = received.split("--");
            flag = args[0];

            if(flag.equals(Mensajes.PETICION_MAPA_LUDOTECAS_MOVIL_CORRECTA)){

                int nLudotecas = Integer.parseInt(args[1]);

                for( int i= 0 ; i< nLudotecas ; i++){
                    received = SocketHandler.getIn().readLine();
                    args = received.split("--");
                    Ludoteca ludoteca = new Ludoteca();
                    ludoteca.setId(Integer.parseInt(args[0]));
                    ludoteca.setNombre(args[1]);
                    ludoteca.setDireccion(args[2]);
                    ludoteca.setLatitud(Double.parseDouble(args[3]));
                    ludoteca.setLongitud(Double.parseDouble(args[4]));
                    ludoteca.setTelefono(args[5]);
                    ludoteca.setFechaCreacion(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").parse(args[6])));
                    ludoteca.setNombrePropietario(args[7]);
                    ludoteca.setApellidosPropietario(args[8]);

                    listaLudotecas.add(ludoteca);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions if they were not granted
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return;
        }
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new CustomInfoWindow(getActivity()));



        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                });

        for(Ludoteca ludoteca : listaLudotecas){

            LatLng latLng = new LatLng(ludoteca.getLatitud(),ludoteca.getLongitud());

            mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(ludoteca.getNombre())
                            .snippet(ludoteca.getDireccion()));
            //mMap.addMarker(new MarkerOptions().position(latLng).title(ludoteca.getNombre()));
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                for (Ludoteca ludoteca : listaLudotecas) {
                    if (ludoteca.getNombre().equals(marker.getTitle())) {
                        Intent intent = new Intent(getActivity(), LudotecaActivity.class);
                        intent.putExtra("ludoteca", (Serializable) ludoteca);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });

    }


}