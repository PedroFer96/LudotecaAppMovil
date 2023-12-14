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
import com.pedrofernandez.appmovilludoteca.model.Actividades;

import java.util.ArrayList;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder>{

    ArrayList<Actividades> listaActividades;
    private Context context;
    private OnButtonClickedListener listener;

    public ActividadesAdapter(Context context, ArrayList<Actividades> listaActividades, OnButtonClickedListener listener) {
        this.context = context;
        this.listaActividades = listaActividades;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_row_actividades_ludotecas,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesAdapter.ViewHolder holder, int position) {

        final Actividades actividades = listaActividades.get(position);

        holder.nombreActividad.setText(listaActividades.get(position).getNombreTipo());
        holder.fechaActividad.setText(listaActividades.get(position).getFecha());
        holder.horaActividad.setText(listaActividades.get(position).getHora());
        holder.nombreSala.setText(listaActividades.get(position).getSala());
        holder.nombreReponsable.setText(listaActividades.get(position).getNombre() + "  " + listaActividades.get(position).getApellidos());
        holder.btnInscribirse.setText("Inscribirse: " + listaActividades.get(position).getPrecio() + "€" );

        holder.btnInscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,actividades);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Actividades actividad);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView tarjeta;
        TextView nombreActividad, fechaActividad, horaActividad;
        TextView nombreSala, nombreReponsable;
        Button btnInscribirse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tarjeta = itemView.findViewById(R.id.cardViewActividadLudoteca);
            nombreActividad = itemView.findViewById(R.id.textViewActividadLudotecaNombre);
            fechaActividad = itemView.findViewById(R.id.textViewActividadLudotecaDia);
            horaActividad = itemView.findViewById(R.id.textViewActividadLudotecaHora);
            nombreSala = itemView.findViewById(R.id.textViewActividadLudotecaSala);
            nombreReponsable = itemView.findViewById(R.id.textViewActividadLudotecaEmpleado);
            btnInscribirse = itemView.findViewById(R.id.buttonInscribirseActividad);
        }
    }
}
