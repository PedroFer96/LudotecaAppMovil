package com.pedrofernandez.appmovilludoteca.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.activities.InicioContenedor;
import com.pedrofernandez.appmovilludoteca.adapter.ActividadesAdapter;
import com.pedrofernandez.appmovilludoteca.model.Actividades;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActividadesLudotecaFragment extends Fragment implements ActividadesAdapter.OnButtonClickedListener{

    ArrayList<Actividades> listaActividades = new ArrayList<>();
    private Application application;
    Ludoteca ludoteca;
    TextView emptyView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ActividadesAdapter adapter;

    public static final String SHARED_PREFS = "sharedPrefs";

    int idActividad;
    int idTipo;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId("AXjlaSlnqjxlLj2ChvQwGls4rITU9XZN45adP3EdpKu6TrQ9og_NWgzXrK-FZocPPaMjy_HFd_TKCf0Z");

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getContext().MODE_PRIVATE);
                    int idUsuario = sharedPreferences.getInt("id", -1);
                    if (result.getResultCode() == PaymentActivity.RESULT_OK) {

                        Toast.makeText(getActivity(), "Pago realizado correctamente", Toast.LENGTH_SHORT).show();

                        SocketHandler.getOut().println(Mensajes.PETICION_PAGO_ACTIVIDAD_REALIZADA_MOVIL + "--" + idActividad + "--" + idTipo + "--" + idUsuario);

                        Intent intent = new Intent(getActivity(), InicioContenedor.class);
                        startActivity(intent);



                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(getActivity(), "Pago cancelado", Toast.LENGTH_SHORT).show();
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Toast.makeText(getActivity(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    public ActividadesLudotecaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actividades_ludoteca, container, false);
        Bundle args = getArguments();
        ludoteca = (Ludoteca) args.getSerializable("ludoteca");
        emptyView = view.findViewById(R.id.emptyViewActividadesLudoteca);

        application = getActivity().getApplication();

        listaActividades = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_viewActividadesLudoteca);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        cargarActividades();

        adapter = new ActividadesAdapter(getContext(), listaActividades, this);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onButtonClicked(View v, Actividades actividad) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getContext().MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("id", -1);
        int idTutor = sharedPreferences.getInt("idTutor", 0);
        int codSeguridad = sharedPreferences.getInt("codSeguridad", -1);
        if(v.getId() == R.id.buttonInscribirseActividad){

            if(idUsuario != -1){

                if(idTutor != 0){

                    final EditText input = new EditText(getContext());
                    input.setInputType(InputType.TYPE_CLASS_NUMBER  | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Confirmación")
                            .setMessage("Por favor, ingrese el número de confirmación del cliente:")
                            .setView(input) // Añadir el EditText al AlertDialog
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Acción a realizar cuando se presiona el botón "OK"

                                    String userInput = input.getText().toString();

                                    if( userInput.equals(String.valueOf(codSeguridad))){

                                        //Toast.makeText(getActivity(), "El código de seguridad es correcto", Toast.LENGTH_SHORT).show();
                                        inscripcionesActividad(actividad, idUsuario);

                                    }else {
                                        Toast.makeText(getActivity(), "El código de seguridad no es correcto", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Acción a realizar cuando se presiona el botón "Cancelar"
                                    dialog.cancel();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else {
                    inscripcionesActividad(actividad, idUsuario);
                }

            }else{
                Toast.makeText(getContext(), "Primero debes de iniciar sesión", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void inscripcionesActividad(Actividades actividad, int idUsuario){

        SocketHandler.getOut().println(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL + "--" + actividad.getId() + "--" + actividad.getTipo() + "--" + idUsuario + "--" + ludoteca.getId());

        try {
            String received;
            String flag = "";
            String[] args;
            received = SocketHandler.getIn().readLine();
            args = received.split("--");
            flag = args[0];

            if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL_ERROR_PREINSCRITO)){
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Usted aun no se ha inscrito en ninguna Ludoteca").setPositiveButton("Aceptar", null).show();
            }
            if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL_ERROR_VALIDAR)){
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Su inscripción aun no ha sido validada").setPositiveButton("Aceptar", null).show();
            }
            if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL_ERROR_INSCRIPCIONLUDOTECA)){
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Usted no está inscrito en esta Ludoteca").setPositiveButton("Aceptar", null).show();
            }
            if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL_ERROR_SINESPACIO)){
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("No hay espacio en esta actividad").setPositiveButton("Aceptar", null).show();
            }
            if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL_ERROR_INSCRIPCIONACTIVIDAD)){
                new AlertDialog.Builder(getContext()).setTitle("Error").setMessage("Usted ya está inscrito en esta actividad").setPositiveButton("Aceptar", null).show();
            }
            if(flag.equals(Mensajes.PETICION_INSCRIBIRSE_ACTIVIDAD_LUDOTECA_MOVIL_CORRECTO)){
                //Toast.makeText(getContext(), "Aqui nos llevara a Paypalactivity", Toast.LENGTH_SHORT).show();
                //Datos que cargamos para si el pago es correcto hacer la llamada al servidor
                idActividad = actividad.getId();
                idTipo = actividad.getTipo();
                int precio = actividad.getPrecio();
                PayPalPayment payment = new PayPalPayment(new BigDecimal(precio), "EUR", "Pago Actividad: " + actividad.getNombreTipo(), PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(getContext(), PaymentActivity.class);

                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                mStartForResult.launch(intent);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void cargarActividades(){


        try {

            SocketHandler.getOut().println(Mensajes.PETICION_OBTENER_ACTIVIDADES_LUDOTECA_MOVIL + "--" + ludoteca.getId());

            String received ;
            String flag ="";
            String [] args ;

            received = SocketHandler.getIn().readLine();
            args = received.split("--");
            flag = args[0];

            if(flag.equals(Mensajes.PETICION_OBTENER_ACTIVIDADES_LUDOTECA_MOVIL_CORRECTO)){

                int nActividades = Integer.parseInt(args[1]);

                for(int i = 0; i< nActividades; i++){
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
                    actividad.setPrecio(Integer.parseInt(args[9]));

                    listaActividades.add(actividad);
                }

            }

            if (listaActividades.size() == 0){
                emptyView.setText("No hay proximas actividades programadas");
            }else {
                emptyView.setVisibility(View.GONE);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}