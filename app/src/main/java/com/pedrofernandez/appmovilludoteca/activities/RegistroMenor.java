package com.pedrofernandez.appmovilludoteca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.utils.DatePickerFragment;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistroMenor extends AppCompatActivity {

    EditText fechaNacimientoNino;
    EditText fechaNacimientoTutor;
    boolean validacionEmail = false;
    boolean validacionDNI = false;
    boolean validacionDNITutor = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_menor);


        Button btnRegistro = (Button) findViewById(R.id.btnRegistro);

        fechaNacimientoNino = (EditText) findViewById(R.id.edFecha);
        fechaNacimientoNino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogNiño();
            }
        });

        fechaNacimientoTutor = (EditText) findViewById(R.id.edFechaTutor);
        fechaNacimientoTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogTutor();
            }
        });

        EditText email = (EditText) findViewById(R.id.edCorreo);
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isEmailValid(email.getText().toString())) {
                        validacionEmail = false;
                        email.setError("Email no válido");
                    }else {
                        validacionEmail = true;
                    }
                }
            }
        });

        EditText dni = (EditText) findViewById(R.id.edDNI);
        if(dni.getText().length() > 0 ){
            dni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        if (!isDniValid(dni.getText().toString())) {
                            validacionDNI = false;
                            dni.setError("DNI no válido");
                        }else {
                            validacionDNI = true;
                        }
                    }
                }
            });
        }else{
            validacionDNI = true;
        }


        EditText dniTutor = (EditText) findViewById(R.id.edDNITutor);
        dniTutor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isDniValid(dniTutor.getText().toString())) {
                        validacionDNITutor = false;
                        dniTutor.setError("DNI no válido");
                    }else{
                        validacionDNITutor = true;
                    }
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });



    }


    private void registrar (){

        final EditText nombreniño = (EditText) findViewById(R.id.edNombre);
        final EditText apellidosniño = (EditText) findViewById(R.id.edApellidos);
        final EditText fechanacimientoniño = (EditText) findViewById(R.id.edFecha);
        final EditText dniniño = (EditText) findViewById(R.id.edDNI);
        final EditText telefononiño = (EditText) findViewById(R.id.edTelefono);
        final EditText emailniño = (EditText) findViewById(R.id.edCorreo);
        final EditText user = (EditText) findViewById(R.id.edUsuario);
        final EditText pass = (EditText) findViewById(R.id.edContrasena);
        final EditText nombretutor = (EditText) findViewById(R.id.edNombreTutor);
        final EditText apellidostutor = (EditText) findViewById(R.id.edApellidosTutor);
        final EditText fechanacimientotutor = (EditText) findViewById(R.id.edFechaTutor);
        final EditText dnitutor = (EditText) findViewById(R.id.edDNITutor);
        final EditText telefonotutor = (EditText) findViewById(R.id.edTelefonoTutor);
        final EditText condSeguridad = (EditText) findViewById(R.id.edCodSeguridad);


        //13 campos, ya que dniniño puede estar null
        if(nombreniño.getText().length()>0 && apellidosniño.getText().length()>0 && fechanacimientoniño.getText().length()>0  && telefononiño.getText().length()>0 && emailniño.getText().length()>0 && user.getText().length()>0 && pass.getText().length()>0 && nombretutor.getText().length()>0 && apellidostutor.getText().length()>0 && fechanacimientotutor.getText().length()>0 && dnitutor.getText().length()>0 && telefonotutor.getText().length()>0 && condSeguridad.getText().length()>0){

            String inputDateStr = fechanacimientoniño.getText().toString();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date inputDate = null;
            try {
                inputDate = format.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar eigthteenYearsAgo = Calendar.getInstance();
            eigthteenYearsAgo.add(Calendar.YEAR, -18);

            if(inputDate.before(eigthteenYearsAgo.getTime())){
                Toast.makeText(this, "El niño debe ser menor de edad", Toast.LENGTH_SHORT).show();

            }else{

                String inputDateStrTutor = fechanacimientotutor.getText().toString();
                DateFormat formatTutor = new SimpleDateFormat("dd/MM/yyyy");

                Date inputDateTutor = null;
                try {
                    inputDateTutor = formatTutor.parse(inputDateStrTutor);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar eigthteenYearsAgoTutor = Calendar.getInstance();
                eigthteenYearsAgoTutor.add(Calendar.YEAR, -18);

                if(inputDateTutor.after(eigthteenYearsAgoTutor.getTime())) {
                    Toast.makeText(this, "El tutor debe ser mayor de edad", Toast.LENGTH_SHORT).show();
                }else{
                    if(validacionDNI && validacionDNITutor && validacionEmail){
                        try{
                            //Toast.makeText(this, "Registro correcto", Toast.LENGTH_SHORT).show();
                            SocketHandler.getOut().println(Mensajes.PETICION_REGISTRO_MENOR_MOVIL + "--" + nombreniño.getText() + "--" + apellidosniño.getText() + "--" + fechanacimientoniño.getText() + "--" + dniniño.getText() + "--" + telefononiño.getText() + "--" + emailniño.getText() + "--" + user.getText() + "--" + pass.getText() + "--" + nombretutor.getText() + "--" + apellidostutor.getText() + "--" + fechanacimientotutor.getText() + "--" + dnitutor.getText() + "--" + telefonotutor.getText() + "--" + condSeguridad.getText());
                            registroCorrecto();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Rellene todos los campos Correctamente", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        } else {
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }



    }

    private void registroCorrecto() {

        try {
            String received = SocketHandler.getIn().readLine();
            System.out.println("dentro de registro");
            System.out.println("dentro de registro");
            System.out.println("dentro de registro");
            System.out.println("dentro de registro");
            System.out.println(received);
            if (received.equals(Mensajes.PETICION_REGISTRO_MENOR_MOVIL_CORRECTO)) {
                Toast.makeText(getApplicationContext(), "Registro correcto", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistroMenor.this, LoginActivity.class);
                startActivity(intent);
            }

            if (received.equals(Mensajes.PETICION_REGISTRO_MENOR_MOVIL_ERROR)) {
                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }


    private void showDatePickerDialogNiño() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fechaNacimientoNino.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showDatePickerDialogTutor() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fechaNacimientoTutor.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public boolean isDniValid(String dni) {
        String dniPattern = "\\d{8}[A-HJ-NP-TV-Z]";
        return dni.matches(dniPattern);
    }


}