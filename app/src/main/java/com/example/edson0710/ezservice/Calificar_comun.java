package com.example.edson0710.ezservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calificar_comun extends AppCompatActivity {

    RatingBar ratingBar;
    Button finalizar;
    EditText comentario;
    String url2, id_uc;
    int id_us;
    RadioGroup radio;
    RadioButton radio1, radio2;
    int pendiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_comun);

        id_uc = getIntent().getExtras().getString("id_uc");
        id_us = getIntent().getExtras().getInt("id_us");

        ratingBar = findViewById(R.id.raitingbar);
        finalizar = findViewById(R.id.btn_calificar);
        comentario = findViewById(R.id.editText2);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                float calificacion = ratingBar.getRating();
                String comentarios = comentario.getText().toString();
                jsoncall(comentarios, calificacion, date);

            }
        });



    }

    public void jsoncall(String comentario,  float calificacion, String date) {
        if (radio1.isChecked()){
            pendiente = 0;
        }
        if (radio2.isChecked()){
            pendiente = 1;
        }

        url2 = "http://ezservice.tech/add_historial_servidor.php?id_uc=" + id_us + "&id_us=" + id_uc
                + "&cali=" + calificacion + "&coment=" + comentario + "&pendiente=" + pendiente;



        JsonObjectRequest peticion = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url2,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String valor = response.getString("Estado");
                                    switch (valor) {
                                        case "OK":
                                            Intent intent = new Intent(Calificar_comun.this, MainServidor.class);
                                            intent.putExtra("id", id_uc);
                                            startActivity(intent);                                            break;
                                        case "NO":

                                            Toast.makeText(Calificar_comun.this, "No se permiten groserias", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Calificar_comun.this, "Error php", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(Calificar_comun.this);
        x.add(peticion);

    }
}
