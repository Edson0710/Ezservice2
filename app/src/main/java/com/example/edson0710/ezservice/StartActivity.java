package com.example.edson0710.ezservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button login, register;
    int type_obtener;

    private static final String STRING_PREFERENCES = "preferencias";
    private static final String PREFERENCE_ESTADO_BUTTON = "estado.button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (obtenerEstado()) {
            String id_obtener = obtenerId();
            type_obtener = obtenerTipo();
            if (type_obtener == 1) {
                Intent iniciarAdmin = new Intent(StartActivity.this, MainActivity.class);
                iniciarAdmin.putExtra("id", id_obtener);
                startActivity(iniciarAdmin);
                finish();
            }
            if (type_obtener == 2) {
                Intent iniciarAdmin = new Intent(StartActivity.this, MainServidor.class);
                iniciarAdmin.putExtra("id", id_obtener);
                startActivity(iniciarAdmin);
                finish();
            }
        }

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, login1.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, Registros.class));
            }
        });

    }
    @Override
    public void onBackPressed() {

    }

    public int obtenerTipo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
        int type_preference = preferences.getInt("TIPO", 1);
        return type_preference;
    }

    public String obtenerId() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
        String id_preference = preferences.getString("ID", "1");
        return id_preference;

    }

    public boolean obtenerEstado() {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCE_ESTADO_BUTTON, false);
    }

}