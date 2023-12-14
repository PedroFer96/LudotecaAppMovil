package com.pedrofernandez.appmovilludoteca.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pedrofernandez.appmovilludoteca.MainActivity;
import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.fragments.InicioFragment;
import com.pedrofernandez.appmovilludoteca.utils.Mensajes;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;
import com.pedrofernandez.appmovilludoteca.utils.UsuarioActual;

import java.io.IOException;

public class LoginActivity extends MenuActivity {

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        invalidateOptionsMenu();



        final EditText usuario = findViewById(R.id.nombreUser);
        final EditText password = findViewById(R.id.passwordUser);
        final Button btnlogin = findViewById(R.id.btnLogin);
        final TextView registro = findViewById(R.id.registrarse);

        btnlogin.setOnClickListener(v -> {

            if(usuario.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            }else{
                login(usuario.getText().toString(), password.getText().toString());
            }
        });



        registro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PreguntaRegister.class);
            startActivity(intent);
        });

    }

    private void login(String usuario, String contraseña) {

        try{
            SocketHandler.getOut().println(Mensajes.PETICION_LOGIN_CLIENTE_MOVIL + "--" + usuario + "--" + contraseña);
            String received ;
            String flag = "";
            String [] args ;

            received = SocketHandler.getIn().readLine();
            args = received.split("--");
            flag = args[0];


            if(flag.equals(Mensajes.PETICION_LOGIN_CLIENTE_MOVIL_CORRECTO)) {

                Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", Integer.parseInt(args[1]));
                editor.putString("nombreC", args[2]);
                editor.putString("apellidosC", args[3]);
                editor.putString("fechaNacimientoC", args[4]);
                editor.putString("dniC", args[5]);
                editor.putString("phoneC", args[6]);
                editor.putString("correo", args[7]);
                editor.putString("user", args[8]);
                editor.putString("password", args[9]);
                editor.putInt("estado", Integer.parseInt(args[10]));
                editor.putInt("idTutor", Integer.parseInt(args[11]));
                editor.putString("nombreT", args[12]);
                editor.putString("apellidosT", args[13]);
                editor.putString("fechaNacimientoTutor", args[14]);
                editor.putString("dniT", args[15]);
                editor.putInt("phoneT", Integer.parseInt(args[16]));
                editor.putInt("codSeguridad", Integer.parseInt(args[17]));
                editor.apply();



                Intent intent = new Intent(LoginActivity.this, InicioContenedor.class);
                startActivity(intent);
                finish();


            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            Toast.makeText(this, "Error al conectar el servidor", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isUserLogged = false;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nCheck = sharedPreferences.getInt("id", -1);

        if (nCheck != -1) {
            isUserLogged = true;
        }
        menu.removeItem(R.id.Login);

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