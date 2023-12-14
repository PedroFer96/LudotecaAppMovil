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
import com.pedrofernandez.appmovilludoteca.fragments.InicioFragment;
import com.pedrofernandez.appmovilludoteca.model.Actividades;

import java.util.ArrayList;

public class ActividadesInscritasAdapter extends RecyclerView.Adapter<ActividadesInscritasAdapter.ViewHolder> {

    ArrayList<Actividades> listActividadesInscritas;
    private Context context;
    private OnButtonClickedListener listener;
    public ActividadesInscritasAdapter(Context context, ArrayList<Actividades> listActividades, OnButtonClickedListener listener) {
        this.context = context;
        this.listActividadesInscritas = listActividades;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ActividadesInscritasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_actividades_inscritas,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesInscritasAdapter.ViewHolder holder, int position) {
        final Actividades actividades = listActividadesInscritas.get(position);

        holder.nombreActividad.setText(listActividadesInscritas.get(position).getNombreTipo());
        holder.fechaActividad.setText(listActividadesInscritas.get(position).getFecha());
        holder.horaActividad.setText(listActividadesInscritas.get(position).getHora());
        holder.nombreSala.setText(listActividadesInscritas.get(position).getSala());
        holder.nombreReponsable.setText(listActividadesInscritas.get(position).getNombre() + "  " + listActividadesInscritas.get(position).getApellidos());

        holder.btnAnularInscripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(v,actividades);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listActividadesInscritas.size();
    }

    public interface OnButtonClickedListener{
        void onButtonClicked(View v, Actividades actividades);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView tarjeta;
        TextView nombreActividad, fechaActividad, horaActividad;
        TextView nombreSala, nombreReponsable;
        Button btnAnularInscripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tarjeta = itemView.findViewById(R.id.cardViewActividadLudoteca);
            nombreActividad = itemView.findViewById(R.id.textViewActividadInscritaNombre);
            fechaActividad = itemView.findViewById(R.id.textViewActividadInscritaDia);
            horaActividad = itemView.findViewById(R.id.textViewActividadInscritaHora);
            nombreSala = itemView.findViewById(R.id.textViewActividadInscritaSala);
            nombreReponsable = itemView.findViewById(R.id.textViewActividadInscritaEmpleado);
            btnAnularInscripcion = itemView.findViewById(R.id.buttonabandonarActividadInscrita);
        }
    }
}
