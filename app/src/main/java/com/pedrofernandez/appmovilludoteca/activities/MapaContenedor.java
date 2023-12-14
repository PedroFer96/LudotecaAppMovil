package com.pedrofernandez.appmovilludoteca.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pedrofernandez.appmovilludoteca.R;

public class MapaContenedor extends MenuActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_contenedor);
        //invalidateOptionsMenu();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isUserLogged = false;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nCheck = sharedPreferences.getInt("id", -1);

        if (nCheck != -1) {
            isUserLogged = true;
        }

        menu.removeItem(R.id.Mapa);

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