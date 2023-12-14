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

public class RegistroMayor extends AppCompatActivity {

    EditText fechaNacimiento;
    boolean validacionEmail = false;
    boolean validacionDni = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_mayor);

        Button btnRegistro = (Button) findViewById(R.id.btnRegistro);

        fechaNacimiento = (EditText) findViewById(R.id.edFecha);
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
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
                    } else{
                        validacionEmail = true;
                    }
                }
            }
        });

        EditText dni = (EditText) findViewById(R.id.edDNI);
        dni.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!isDniValid(dni.getText().toString())) {
                        validacionDni = false;
                        dni.setError("DNI no válido");
                    } else {
                        validacionDni = true;
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


    private void registrar(){

        final EditText nombre = (EditText) findViewById(R.id.edNombre);
        final EditText apellidos = (EditText) findViewById(R.id.edApellidos);
        final EditText fechaNacimiento = (EditText) findViewById(R.id.edFecha);
        final EditText dni = (EditText) findViewById(R.id.edDNI);
        final EditText telefono = (EditText) findViewById(R.id.edTelefono);
        final EditText email = (EditText) findViewById(R.id.edCorreo);
        final EditText usuario = (EditText) findViewById(R.id.edUsuario);
        final EditText contrasena = (EditText) findViewById(R.id.edContrasena);

        if(nombre.getText().length()>0 && apellidos.getText().length()>0 && fechaNacimiento.getText().length()>0 && dni.getText().length()>0 && telefono.getText().length()>0 && email.getText().length()>0 && usuario.getText().length()>0 && contrasena.getText().length()>0){

            String inputDateStr = fechaNacimiento.getText().toString();
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date inputDate = null;
            try {
                inputDate = format.parse(inputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar eigthteenYearsAgo = Calendar.getInstance();
            eigthteenYearsAgo.add(Calendar.YEAR, -18);

            if(inputDate.after(eigthteenYearsAgo.getTime())){
                Toast.makeText(getApplicationContext(), "Debes ser mayor de edad", Toast.LENGTH_SHORT).show();
            }else{

                if(validacionEmail && validacionDni){

                    try{
                        SocketHandler.getOut().println(Mensajes.PETICION_REGISTRO_MAYOR_MOVIL + "--" + nombre.getText().toString() + "--" + apellidos.getText().toString() + "--" + fechaNacimiento.getText().toString() + "--" + dni.getText().toString() + "--" + telefono.getText().toString() + "--" + email.getText().toString() + "--" + usuario.getText().toString() + "--" + contrasena.getText().toString());
                        registroCorrecto();
                    }catch (NullPointerException e){
                        Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Rellene todos los campos Correctamente", Toast.LENGTH_SHORT).show();
                }

            }

        }else {
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
            System.out.println("Registro correcto");


    }

    private void registroCorrecto() {

        try{
            String received = SocketHandler.getIn().readLine();

            if(received.equals(Mensajes.PETICION_REGISTRO_MAYOR_MOVIL_CORRECTO)) {
                Toast.makeText(getApplicationContext(), "Registro correcto", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistroMayor.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            if(received.equals(Mensajes.PETICION_REGISTRO_MAYOR_MOVIL_ERROR)){
                Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fechaNacimiento.setText(selectedDate);
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