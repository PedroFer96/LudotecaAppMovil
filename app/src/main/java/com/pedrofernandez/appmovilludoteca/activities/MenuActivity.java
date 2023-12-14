package com.pedrofernandez.appmovilludoteca.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pedrofernandez.appmovilludoteca.MainActivity;
import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.fragments.MapaFragment;
import com.pedrofernandez.appmovilludoteca.utils.UsuarioActual;

public class MenuActivity extends AppCompatActivity {

    boolean isUserLogged = false;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean isUserLogged = checkUserLoginStatus();

        if(isUserLogged) {
            menu.removeItem(R.id.Login);
        }else{
            menu.removeItem(R.id.Inicio);
            menu.removeItem(R.id.Perfil);
            menu.removeItem(R.id.Desconectar);
        }


        return super.onPrepareOptionsMenu(menu);

    }

    private boolean checkUserLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nCheck = sharedPreferences.getInt("id", -1);
        if(nCheck != -1){
            return true;
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.Login:
                //este debe desaparecer cuando estemos logeados
                Intent intentLogin = new Intent(this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.Inicio:
                //Solo deberia salir cuando estemos logeado
                Intent intentInicio = new Intent(this, InicioContenedor.class);
                startActivity(intentInicio);
                break;
            case R.id.Perfil:
                //Solo deberia salir cuando estemos logeado
                Intent intentPerfil = new Intent(this, PerfilActivity.class);
                startActivity(intentPerfil);
                break;
            case R.id.Mapa:
                Intent intentMapa = new Intent(this, MapaContenedor.class);
                startActivity(intentMapa);
                break;
            case R.id.ListaLudotecas:
                Intent intentListaLudotecas = new Intent(this, ListaLudotecasContenedor.class);
                startActivity(intentListaLudotecas);
                break;

            case R.id.Desconectar:
                //Solo deberia salir cuando estemos logeado NO FUNCIONA
                SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
