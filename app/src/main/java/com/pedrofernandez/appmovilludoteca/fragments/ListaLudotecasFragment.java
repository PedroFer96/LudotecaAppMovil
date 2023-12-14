package com.pedrofernandez.appmovilludoteca.fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.activities.LudotecaActivity;
import com.pedrofernandez.appmovilludoteca.adapter.LudotecasAdapter;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ListaLudotecasFragment extends Fragment implements LudotecasAdapter.OnButtonClickedListener {

    ArrayList<Ludoteca> listaludotecas = new ArrayList<>();
    private Application application;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LudotecasAdapter adapter;


    public ListaLudotecasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_ludotecas, container, false);
        application = getActivity().getApplication();
        listaludotecas = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_viewListaLudotecas);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        cargarLudotecas();

        adapter = new LudotecasAdapter(getContext(), listaludotecas, (LudotecasAdapter.OnButtonClickedListener) this);

        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onButtonClicked(View v, Ludoteca ludoteca){
        if(v.getId() == R.id.buttonVerLudoteca){

            Intent intentLudoteca = new Intent(getActivity(), LudotecaActivity.class);
            intentLudoteca.putExtra("ludoteca", (Serializable) ludoteca);
            startActivity(intentLudoteca);

        }

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

                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = formatter.parse(args[6]);
                        SimpleDateFormat formatSdf = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = formatSdf.format(date);
                        // Me hace el cambio pero luego lo muestra al revés
                        ludoteca.setFechaCreacion(formattedDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ludoteca.setNombrePropietario(args[7]);
                    ludoteca.setApellidosPropietario(args[8]);

                    listaludotecas.add(ludoteca);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}