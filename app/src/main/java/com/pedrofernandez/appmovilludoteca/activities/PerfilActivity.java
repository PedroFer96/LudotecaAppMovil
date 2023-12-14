package com.pedrofernandez.appmovilludoteca.activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.utils.DatePickerFragment;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PerfilActivity extends MenuActivity {
    EditText nombreCPerfil, apellidosCPerfil, fechaNCPerfil, dniCPerfil, telefonoCPerfil;
    EditText correoPerfil, usuarioPerfil, passwordPerfil, estadoPerfil,nombreTPerfil, apellidosTPerfil;
    EditText fechaNTPerfil, dniTPerfil, telefonoTPerfil, codSeguridadPerfil;
    Button btneditar ;
    Button btncancelar ;
    Button btnActualizar;

     List<EditText> editTexts ;


    public static final String SHARED_PREFS = "sharedPrefs";
    int estado =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        nombreCPerfil = findViewById(R.id.edNombrePerfil);
        apellidosCPerfil = findViewById(R.id.edApellidosPerfil);
        fechaNCPerfil = findViewById(R.id.edFechaPerfil);
        dniCPerfil = findViewById(R.id.edDniPerfil);
        telefonoCPerfil = findViewById(R.id.edTelefonoPerfil);
        correoPerfil = findViewById(R.id.edCorreoPerfil);
        usuarioPerfil = findViewById(R.id.edUsuarioPerfil);
        passwordPerfil = findViewById(R.id.edContrasenaPerfil);
        estadoPerfil = findViewById(R.id.edEstadoPerfil);
        nombreTPerfil = findViewById(R.id.edNombreTutorPerfil);
        apellidosTPerfil = findViewById(R.id.edApellidosTutorPerfil);
        fechaNTPerfil = findViewById(R.id.edFechaTutorPerfil);
        dniTPerfil = findViewById(R.id.edDNITutorPerfil);
        telefonoTPerfil = findViewById(R.id.edTelefonoTutorPerfil);
        codSeguridadPerfil = findViewById(R.id.edCodSeguridadPerfil);

        editTexts = Arrays.asList(
                findViewById(R.id.edNombrePerfil),
                findViewById(R.id.edApellidosPerfil),
                findViewById(R.id.edDniPerfil),
                findViewById(R.id.edTelefonoPerfil),
                findViewById(R.id.edCorreoPerfil),
                findViewById(R.id.edUsuarioPerfil),
                findViewById(R.id.edContrasenaPerfil),
                findViewById(R.id.edNombreTutorPerfil),
                findViewById(R.id.edApellidosTutorPerfil),
                findViewById(R.id.edDNITutorPerfil),
                findViewById(R.id.edTelefonoTutorPerfil),
                findViewById(R.id.edCodSeguridadPerfil)
        );

        btneditar = findViewById(R.id.btnEditarPerfil);
        btncancelar = findViewById(R.id.btnCancelarPerfil);
        btnActualizar = findViewById(R.id.btnActualizarPerfil);


        int id = sharedPreferences.getInt("id", -1);
        String nombreC = sharedPreferences.getString("nombreC", "");
        String apellidosC = sharedPreferences.getString("apellidosC", "");
        String fechaNacimientoC = sharedPreferences.getString("fechaNacimientoC", "");
        String dniC = sharedPreferences.getString("dniC", "");
        String phoneC = sharedPreferences.getString("phoneC", "");
        String correo = sharedPreferences.getString("correo", "");
        String user = sharedPreferences.getString("user", "");
        String password = sharedPreferences.getString("password", "");

        //Fecha Nacimiento

        String fechaFormateada = modificarFecha(fechaNacimientoC);

        nombreCPerfil.setText(nombreC);
        apellidosCPerfil.setText(apellidosC);
        fechaNCPerfil.setText(fechaFormateada);
        dniCPerfil.setText(dniC);
        telefonoCPerfil.setText(phoneC);
        correoPerfil.setText(correo);
        usuarioPerfil.setText(user);
        passwordPerfil.setText(password);


        cargarEstado(id);

        if (estado == 1) {
            estadoPerfil.setText("Validado");
            estadoPerfil.setTextColor(Color.GREEN);
        } else{
            estadoPerfil.setText("Sin Validar");
            estadoPerfil.setTextColor(Color.RED);
        }


        int idTutor = sharedPreferences.getInt("idTutor", 0);

        if (idTutor == 0){
            LinearLayout perfilTutor = findViewById(R.id.perfilTutor);
            perfilTutor.setVisibility(View.GONE);
        }
        if(idTutor != 0){
            String nombreT = sharedPreferences.getString("nombreT", "");
            String apellidosT = sharedPreferences.getString("apellidosT", "");
            String fechaNacimientoTutor = sharedPreferences.getString("fechaNacimientoTutor", "");
            String dniT = sharedPreferences.getString("dniT", "");
            int phoneT = sharedPreferences.getInt("phoneT", -1);
            int codSeguridad = sharedPreferences.getInt("codSeguridad", -1);
            LinearLayout perfilTutor = findViewById(R.id.perfilTutor);
            perfilTutor.setVisibility(View.VISIBLE);

            //Fecha Nacimiento Tutor

            String fechaFormateadaTutor = modificarFecha(fechaNacimientoTutor);

            nombreTPerfil.setText(nombreT);
            apellidosTPerfil.setText(apellidosT);
            fechaNTPerfil.setText(fechaFormateadaTutor);
            dniTPerfil.setText(dniT);
            telefonoTPerfil.setText(String.valueOf(phoneT));
            codSeguridadPerfil.setText(String.valueOf(codSeguridad));
        }


        Button btneditar = findViewById(R.id.btnEditarPerfil);
        Button btncancelar = findViewById(R.id.btnCancelarPerfil);
        Button btnActualizar = findViewById(R.id.btnActualizarPerfil);

        btneditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btneditar.setVisibility(View.GONE);
                btncancelar.setVisibility(View.VISIBLE);
                btnActualizar.setVisibility(View.VISIBLE);

                //edit
                for (EditText editText : editTexts) {
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                }
                fechaNCPerfil.setClickable(true);
                fechaNTPerfil.setClickable(true);

            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btneditar.setVisibility(View.VISIBLE);
                btncancelar.setVisibility(View.GONE);
                btnActualizar.setVisibility(View.GONE);

                //edit
                for (EditText editText : editTexts) {
                    editText.setFocusable(false);
                    editText.clearFocus();
                }
                fechaNCPerfil.setClickable(false);
                fechaNTPerfil.setClickable(false);
            }
        });


        fechaNCPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDatePickerDialogNiño();
            }
        });


        fechaNTPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDatePickerDialogTutor();
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarPerfil(idTutor, id);
            }
        });

    }

    private void actualizarPerfil(int idTutor, int idCliente) {

        String nombreC = nombreCPerfil.getText().toString();
        String apellidosC = apellidosCPerfil.getText().toString();
        String fechaNacimientoC = fechaNCPerfil.getText().toString();
        String dniC = dniCPerfil.getText().toString();
        String phoneC = telefonoCPerfil.getText().toString();
        String correo = correoPerfil.getText().toString();
        String user = usuarioPerfil.getText().toString();
        String password = passwordPerfil.getText().toString();


        if(nombreC.isEmpty() || apellidosC.isEmpty() || fechaNacimientoC.isEmpty() || phoneC.isEmpty() || correo.isEmpty() || user.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }else {

            //Por falta de tiempo no se he podido hacer todas las comprobacion de errores de los campos

            if (idTutor != 0) {
                //parte menor con tutor
                String nombreT = nombreTPerfil.getText().toString();
                String apellidosT = apellidosTPerfil.getText().toString();
                String fechaNacimientoTutor = fechaNTPerfil.getText().toString();
                String dniT = dniTPerfil.getText().toString();
                String phoneT = telefonoTPerfil.getText().toString();
                String codSeguridad = codSeguridadPerfil.getText().toString();

                if(nombreT.isEmpty() || apellidosT.isEmpty() || fechaNacimientoTutor.isEmpty() || dniT.isEmpty() || phoneT.isEmpty() || codSeguridad.isEmpty()) {
                    Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        String fechaOriginalCliente = fechaNacimientoC;
                        String fechaOriginalTutor = fechaNacimientoTutor;

                        SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd/MM/yyyy");

                        SimpleDateFormat nuevoFormato = new SimpleDateFormat("yyyy-MM-dd");

                        String fechaFormateadaCliente = "";
                        String fechaFormateadaTutor = "";
                        try {
                            Date fechaCiente = formatoOriginal.parse(fechaOriginalCliente);
                            fechaFormateadaCliente = nuevoFormato.format(fechaCiente);

                            Date fechaTutor = formatoOriginal.parse(fechaOriginalTutor);
                            fechaFormateadaTutor = nuevoFormato.format(fechaTutor);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                        SocketHandler.getOut().println(Mensajes.PETICION_ACTUALIZAR_CLIENTE_MENOR_MOVIL + "--" + nombreC + "--" + apellidosC + "--" + fechaFormateadaCliente + "--" + dniC + "--" + phoneC + "--" + user + "--" + password + "--" + correo + "--" + nombreT + "--" + apellidosT + "--" + fechaFormateadaTutor + "--" + dniT + "--" + phoneT + "--" + codSeguridad + "--" + idCliente  + "--" + idTutor);

                        String received = SocketHandler.getIn().readLine();

                        if(received.equals(Mensajes.PETICION_ACTUALIZAR_CLIENTE_MENOR_MOVIL_CORRECTO)) {
                            Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();

                            //edit
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("nombreC", nombreC);
                            editor.putString("apellidosC", apellidosC);
                            editor.putString("fechaNacimientoC", fechaNacimientoC);
                            editor.putString("dniC", dniC);
                            editor.putString("phoneC", phoneC);
                            editor.putString("correo", correo);
                            editor.putString("user", user);
                            editor.putString("password", password);

                            editor.putString("nombreT", nombreT);
                            editor.putString("apellidosT", apellidosT);
                            editor.putString("fechaNacimientoTutor", fechaNacimientoTutor);
                            editor.putString("dniT", dniT);
                            editor.putInt("phoneT", Integer.parseInt(phoneT));
                            editor.putInt("codSeguridad", Integer.parseInt(codSeguridad));

                            editor.apply();

                            btneditar.setVisibility(View.VISIBLE);
                            btncancelar.setVisibility(View.GONE);
                            btnActualizar.setVisibility(View.GONE);

                            //edit
                            for (EditText editText : editTexts) {
                                editText.setFocusable(false);
                                editText.clearFocus();
                            }

                        }
                        if(received.equals(Mensajes.PETICION_ACTUALIZAR_CLIENTE_MENOR_MOVIL_ERROR)) {
                            Toast.makeText(this, "Error al actualizar, algun dato no es correcto", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            } else {
                //parte mayor

                try{

                    String fechaOriginal = fechaNacimientoC;

                    SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd/MM/yyyy");

                    SimpleDateFormat nuevoFormato = new SimpleDateFormat("yyyy-MM-dd");

                    String fechaFormateada = "";
                    try {
                        Date fecha = formatoOriginal.parse(fechaOriginal);

                        fechaFormateada = nuevoFormato.format(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SocketHandler.getOut().println(Mensajes.PETICION_ACTUALIZAR_CLIENTE_MAYOR_MOVIL + "--" + nombreC + "--" + apellidosC + "--" + fechaFormateada + "--" + dniC + "--" + phoneC + "--" + user + "--" + password + "--" + correo + "--" + idCliente);

                    String received ;
                    received = SocketHandler.getIn().readLine();

                    if(received.equals(Mensajes.PETICION_ACTUALIZAR_CLIENTE_MAYOR_MOVIL_CORRECTO)){
                        Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();

                        //edit
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("nombreC", nombreC);
                        editor.putString("apellidosC", apellidosC);
                        editor.putString("fechaNacimientoC", fechaNacimientoC);
                        editor.putString("dniC", dniC);
                        editor.putInt("phoneC", Integer.parseInt(phoneC));
                        editor.putString("correo", correo);
                        editor.putString("user", user);
                        editor.putString("password", password);

                        editor.apply();

                        btneditar.setVisibility(View.VISIBLE);
                        btncancelar.setVisibility(View.GONE);
                        btnActualizar.setVisibility(View.GONE);

                        //edit
                        for (EditText editText : editTexts) {
                            editText.setFocusable(false);
                            editText.clearFocus();
                        }

                    }
                    if(received.equals(Mensajes.PETICION_ACTUALIZAR_CLIENTE_MAYOR_MOVIL_ERROR)){
                        Toast.makeText(this, "Error al actualizar, algun dato no es correcto", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private String modificarFecha(String fechaNacimiento) {
        String fechaOriginal = fechaNacimiento;
        SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat nuevoFormato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = "";
        try {
            Date fecha = formatoOriginal.parse(fechaOriginal);
            fechaFormateada = nuevoFormato.format(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fechaFormateada;
    }

    private void cargarEstado(int id) {

        try{
            SocketHandler.getOut().println(Mensajes.PETICION_OBTENER_ESTADO_CLIENTE_MOVIL + "--" + id);

            String received ;
            String [] args ;

            received = SocketHandler.getIn().readLine();
            args = received.split("--");
            estado = Integer.parseInt(args[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showDatePickerDialogNiño() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fechaNCPerfil.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showDatePickerDialogTutor() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fechaNTPerfil.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isUserLogged = false;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nCheck = sharedPreferences.getInt("id", -1);

        if (nCheck != -1) {
            isUserLogged = true;
        }

        menu.removeItem(R.id.Perfil);

        if (isUserLogged) {
            menu.removeItem(R.id.Login);
        } else {
            menu.removeItem(R.id.Inicio);
            menu.removeItem(R.id.Perfil);
            menu.removeItem(R.id.Desconectar);
        }


        return super.onPrepareOptionsMenu(menu);
    }
}