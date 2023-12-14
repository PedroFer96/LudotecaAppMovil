package com.pedrofernandez.appmovilludoteca.fragments;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DetallesLudotecaFragment extends Fragment {

    Ludoteca ludoteca;

    TextView nombreLudoteca, direccionLudoteca, fechaAperturaLudoteca, telefonoLudoteca, propietarioLudoteca;
    Button buttonInscribirse;
    public static final String SHARED_PREFS = "sharedPrefs";

    public DetallesLudotecaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        ludoteca = (Ludoteca) args.getSerializable("ludoteca");

        View view = inflater.inflate(R.layout.fragment_detalles_ludoteca, container, false);



        nombreLudoteca = view.findViewById(R.id.textViewDetallesNombreLudoteca);
        direccionLudoteca = view.findViewById(R.id.textViewDetallesDireccionLudoteca);
        fechaAperturaLudoteca = view.findViewById(R.id.textViewDetallesFechaAperturaLudoteca);
        telefonoLudoteca = view.findViewById(R.id.textViewDetallesTelefonoLudoteca);
        propietarioLudoteca = view.findViewById(R.id.textViewDetallesPropietarioLudoteca);
        buttonInscribirse = view.findViewById(R.id.buttonDetallesInscribirse);

        nombreLudoteca.setText(ludoteca.getNombre());
        direccionLudoteca.setText(ludoteca.getDireccion());
        fechaAperturaLudoteca.setText(ludoteca.getFechaCreacion());
        telefonoLudoteca.setText(ludoteca.getTelefono());
        propietarioLudoteca.setText(ludoteca.getNombrePropietario() + " " + ludoteca.getApellidosPropietario());

        buttonInscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscribirseLudoteca();
            }
        });

        return view;
    }

    private void inscribirseLudoteca() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getContext().MODE_PRIVATE);

        int idLudoteca = ludoteca.getId();
        int idUsuario = sharedPreferences.getInt("id", -1);

        //Toast.makeText(getContext(), "Inscribiendose en la ludoteca...  " + idLudoteca + " - " + idUsuario , Toast.LENGTH_SHORT).show();
        if( idUsuario != -1){
            try {

                SocketHandler.getOut().println(Mensajes.PETICION_INSCRIBIRSE_LUDOTECA_MOVIL + "--" + idLudoteca + "--" + idUsuario);

                String received ;
                String flag ="";
                String [] args;
                received = SocketHandler.getIn().readLine();
                args = received.split("--");
                flag = args[0];

                if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_LUDOTECA_MOVIL_ERROR_INSCRITO)){
                    new AlertDialog.Builder(getContext()).setTitle("Información").setMessage("Ya estás inscrito en esta ludoteca").setPositiveButton("Entendido", null).show();
                }
                if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_LUDOTECA_MOVIL_ERROR_NO_CONFIRMADO)){
                    new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("No puedes inscribirte en esta ludoteca porque no has confirmado tu cuenta.\nDiríjase  a la ludoteca: " + args[1] + " que fue donde se inscribió la primera vez, para que allí le validen ").setPositiveButton("Entendido", null).show();
                }
                if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_LUDOTECA_MOVIL_CORRECTO_INSCRITO)){
                    Toast.makeText(getContext(), "Inscripcion correcta", Toast.LENGTH_SHORT).show();
                }
                if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_LUDOTECA_MOVIL_CORRECTO_PREINSCRITO)){
                    new AlertDialog.Builder(getContext()).setTitle("Preinscripción").setMessage("Se ha enviado una solicitud de inscripción a la ludoteca. Presentese allí para que la puedan validar.").setPositiveButton("Entendido", null).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getContext(), "Primero debes de iniciar sesión", Toast.LENGTH_SHORT).show();

        }
    }
}