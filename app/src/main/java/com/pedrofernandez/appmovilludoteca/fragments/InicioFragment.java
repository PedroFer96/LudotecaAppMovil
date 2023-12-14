package com.pedrofernandez.appmovilludoteca.fragments;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.adapter.ActividadesAdapter;
import com.pedrofernandez.appmovilludoteca.adapter.ActividadesInscritasAdapter;
import com.pedrofernandez.appmovilludoteca.model.Actividades;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InicioFragment extends Fragment implements ActividadesInscritasAdapter.OnButtonClickedListener {

    ArrayList<Actividades> listActividades = new ArrayList<>();
    TextView emptyView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ActividadesInscritasAdapter adapter;
    public static final String SHARED_PREFS = "sharedPrefs";
    int idCliente;
    public InicioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_inicio, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE);
        idCliente = sharedPreferences.getInt("id", -1);
        emptyView = view.findViewById(R.id.emptyViewInicio);

        listActividades = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_viewActividadesCliente);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        cargarActividades();

        adapter = new ActividadesInscritasAdapter(getContext(), listActividades, this);
        recyclerView.setAdapter(adapter);



        return view;
    }

    @Override
    public void onButtonClicked(View v, Actividades actividad) {
        new AlertDialog.Builder(getContext())
                .setTitle("Abandonar actividad")
                .setMessage("¿Estás seguro de que quieres abandonar la actividad?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    try {

                        SocketHandler.getOut().println(Mensajes.PETICION_ABANDONAR_ACTIVIDAD_MOVIL + "--" + actividad.getId() + "--" + actividad.getTipo() + "--" + idCliente);

                        String received;
                        String flag = "";
                        String[] args;
                        received = SocketHandler.getIn().readLine();
                        args = received.split("--");
                        flag = args[0];

                        if(flag.equals(Mensajes.PETICION_ABANDONAR_ACTIVIDAD_MOVIL_CORRECTO)){
                            Toast.makeText(getContext(), "Actividad abandonada correctamente", Toast.LENGTH_SHORT).show();
                            listActividades.clear();
                            cargarActividades();
                            adapter.notifyDataSetChanged();
                        }
                        if(flag.equals(Mensajes.PETICION_ABANDONAR_ACTIVIDAD_MOVIL_ERROR)){
                            Toast.makeText(getContext(), "Error al abandonar la actividad", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }


    private void cargarActividades() {

        try{
            SocketHandler.getOut().println(Mensajes.PETICION_OBTENER_ACTIVIDADES_INSCRITAS_MOVIL + "--" + idCliente);

            String received ;
            String flag="";
            String [] args;

            received = SocketHandler.getIn().readLine();
            args = received.split("--");
            flag = args[0];

            if(flag.equals(Mensajes.PETICION_OBTENER_ACTIVIDADES_INSCRITAS_MOVIL_CORRECTO)){

                int nActividades = Integer.parseInt(args[1]);

                for (int i = 0; i< nActividades; i++){
                    received = SocketHandler.getIn().readLine();
                    args = received.split("--");
                    Actividades actividad = new Actividades();

                    actividad.setId(Integer.parseInt(args[1]));
                    String horaSalida = "";
                    try{
                        String horaEntrada = args[2];
                        SimpleDateFormat formatoEntrada = new SimpleDateFormat("HH:mm:ss");
                        Date hora = formatoEntrada.parse(horaEntrada);

                        SimpleDateFormat formatoSalida = new SimpleDateFormat("HH:mm");
                        horaSalida = formatoSalida.format(hora);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    actividad.setHora(horaSalida);
                    String fechaSalida ="";
                    try {
                        String fechaEntrada = args[3];
                        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
                        Date fecha = formatoEntrada.parse(fechaEntrada);

                        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
                        fechaSalida = formatoSalida.format(fecha);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    actividad.setFecha(fechaSalida);
                    actividad.setNombreTipo(args[4]);
                    actividad.setTipo(Integer.parseInt(args[5]));
                    actividad.setNombre(args[6]);
                    actividad.setApellidos(args[7]);
                    actividad.setSala(args[8]);

                    listActividades.add(actividad);

                }

            }

            if(listActividades.size() == 0){
                emptyView.setText("No hay actividades disponibles");
            }else{
                emptyView.setVisibility(View.GONE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}