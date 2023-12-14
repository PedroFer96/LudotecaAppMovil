package com.pedrofernandez.appmovilludoteca.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.pedrofernandez.appmovilludoteca.R;
import com.pedrofernandez.appmovilludoteca.fragments.ActividadesLudotecaFragment;
import com.pedrofernandez.appmovilludoteca.fragments.DetallesLudotecaFragment;
import com.pedrofernandez.appmovilludoteca.fragments.MapaLudotecaFragment;
import com.pedrofernandez.appmovilludoteca.model.Ludoteca;

import java.io.Serializable;

public class LudotecaActivity extends MenuActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    Ludoteca ludoteca;
    private TabLayout tabLayout1;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludoteca);
        invalidateOptionsMenu();

        ludoteca = (Ludoteca) getIntent().getExtras().get("ludoteca");

        String nombreLudoteca = ludoteca.getNombre();


        getSupportActionBar().setTitle(nombreLudoteca);

        tabLayout1 = findViewById(R.id.tabLayout1);
        viewPager2 = findViewById(R.id.viewPager2);

        viewPager2.setAdapter(new AdaptadorFrame(getSupportFragmentManager(), getLifecycle()));
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                tabLayout1.selectTab(tabLayout1.getTabAt(position));
            }
        });

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    class AdaptadorFrame extends FragmentStateAdapter{

        public AdaptadorFrame(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){

                case 0:
                    Fragment fragment = new DetallesLudotecaFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ludoteca", ludoteca);
                    fragment.setArguments(bundle);
                    return fragment;
                case 1:
                    Fragment fragment2 = new ActividadesLudotecaFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("ludoteca",  ludoteca);
                    fragment2.setArguments(bundle2);
                    return fragment2;
                default:
                    Fragment fragment3 = new MapaLudotecaFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable("ludoteca", ludoteca);
                    fragment3.setArguments(bundle3);
                    return fragment3;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
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
        } else {
            menu.removeItem(R.id.Inicio);
            menu.removeItem(R.id.Perfil);
            menu.removeItem(R.id.Desconectar);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}