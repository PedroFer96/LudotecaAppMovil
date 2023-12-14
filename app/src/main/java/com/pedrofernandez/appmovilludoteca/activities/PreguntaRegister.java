package com.pedrofernandez.appmovilludoteca.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.pedrofernandez.appmovilludoteca.R;

public class PreguntaRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_register);

        final Button btnMayor = findViewById(R.id.btnMayor);
        final Button btnMenor = findViewById(R.id.btnMenor);

        btnMayor.setOnClickListener(v -> {
            Intent intent = new Intent(PreguntaRegister.this, RegistroMayor.class);
            startActivity(intent);
        });

        btnMenor.setOnClickListener(v -> {
            Intent intent = new Intent(PreguntaRegister.this, RegistroMenor.class);
            startActivity(intent);

        });

    }
}