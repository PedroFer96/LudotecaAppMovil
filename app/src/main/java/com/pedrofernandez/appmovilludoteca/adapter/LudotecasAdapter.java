package com.pedrofernandez.appmovilludoteca.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.fragments.ListaLudotecasFragment;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;

import java.util.ArrayList;


public class LudotecasAdapter extends RecyclerView.Adapter<LudotecasAdapter.ViewHolder> {

    ArrayList<Ludoteca> listaludotecas;
    private Context context;
    private int layout;
    private OnButtonClickedListener listener;

    public LudotecasAdapter(Context context, ArrayList<Ludoteca> listaludotecas, OnButtonClickedListener listener) {
        this.context = context;
        this.listaludotecas = listaludotecas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_row_lista_ludotecas,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Ludoteca ludoteca = listaludotecas.get(position);

        holder.nLudoteca.setText(listaludotecas.get(position).getNombre());
        holder.direccion.setText(listaludotecas.get(position).getDireccion());
        holder.telefono.setText(listaludotecas.get(position).getTelefono());

        holder.btnVerLudoteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonClicked(view , ludoteca);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaludotecas.size();
    }

    public interface OnButtonClickedListener {
        void onButtonClicked(View v, Ludoteca ludoteca);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView tarjetaL;
        TextView nLudoteca;
        TextView direccion;
        TextView telefono;
        Button btnVerLudoteca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tarjetaL = itemView.findViewById(R.id.cardViewLudoteca);
            nLudoteca = itemView.findViewById(R.id.textViewLudotecaNombre);
            direccion = itemView.findViewById(R.id.textViewLudotecaDireccion);
            telefono = itemView.findViewById(R.id.textViewLudotecaTelefono);
            btnVerLudoteca = itemView.findViewById(R.id.buttonVerLudoteca);
        }

    }



}
