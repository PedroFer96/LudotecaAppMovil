package com.pedrofernandez.appmovilludoteca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import com.pedrofernandez.appmovilludoteca.activities.MenuActivity;
import com.pedrofernandez.appmovilludoteca.fragments.InicioFragment;
import com.pedrofernandez.appmovilludoteca.fragments.MapaFragment;
import com.pedrofernandez.appmovilludoteca.utils.SocketHandler;

import java.net.Socket;

public class MainActivity extends MenuActivity  {

    String hostServerName ="192.168.1.81";
    //String hostServerName ="10.0.2.2";
    int servicePort = 9999;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        conectar();
        
        boolean isUserLogged = checkUserLoginStatus();

        Fragment fragment = null;
        if(isUserLogged){
            fragment = new InicioFragment();
        }else{
            fragment = new MapaFragment();
        }

        showSelectedFragment(fragment);
        
    }

    private void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private boolean checkUserLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int nCheck = sharedPreferences.getInt("id", -1);
        if(nCheck != -1){
            return true;
        }
        return false;
    }

    private void conectar() {

        try {
            System.out.println("Conectando al servidor..." );
            System.out.println("Conectando al servidor..." );

            SocketHandler.setSocket(new Socket(hostServerName, servicePort));
            SocketHandler.setOut();
            SocketHandler.setIn();
        }catch (Exception e){
            System.out.println("Error al conectar al servidor, salta aqui el error de conexion");

            e.printStackTrace();
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

        if (isUserLogged) {
            menu.removeItem(R.id.Login);
            menu.removeItem(R.id.Inicio);
        } else {
            menu.removeItem(R.id.Inicio);
            menu.removeItem(R.id.Mapa);
            menu.removeItem(R.id.Perfil);
            menu.removeItem(R.id.Desconectar);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}