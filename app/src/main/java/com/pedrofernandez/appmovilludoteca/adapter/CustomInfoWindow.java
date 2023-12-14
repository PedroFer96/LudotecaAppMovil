package com.pedrofernandez.appmovilludoteca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.pedrofernandez.appmovilludoteca.R;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter{

    private View view;
    private Context context;

    public CustomInfoWindow(Context context) {
        this.context = context;
        view= LayoutInflater.from(context).inflate(R.layout.marker_map,null);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {

        TextView nombre_ludoteca = view.findViewById(R.id.marker_nombreLudoteca);
        nombre_ludoteca.setText(marker.getTitle());

        TextView direccion_ludoteca = view.findViewById(R.id.marker_direccionLudoteca);
        direccion_ludoteca.setText(marker.getSnippet());

        return view;
    }
}
