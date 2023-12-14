package com.pedrofernandez.appmovilludoteca.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;


import com.pedrofernandez.appmovilludoteca.R;

public class ListaLudotecasContenedor extends MenuActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ludotecas_contenedor);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isUserLogged = false;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nCheck = sharedPreferences.getInt("id", -1);

        if (nCheck != -1) {
            isUserLogged = true;
        }

        menu.removeItem(R.id.ListaLudotecas);

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