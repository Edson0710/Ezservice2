package com.example.edson0710.ezservice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Solicitar extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    Button solicitar, mas, menos, mapa;
    TextView texto, ubicacion;
    String id_uc;
    int distancia = 10;
    private LocationManager locationManager;
    private Location location;
    TextView longitud, latitud;
    double lat, lon;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_solicitar, container, false);
        id_uc = getArguments().getString("id");

        solicitar = (Button) rootView.findViewById(R.id.btn_solicitar);
        texto = (TextView) rootView.findViewById(R.id.tv_solicitar);
        longitud = rootView.findViewById(R.id.longitud);
        latitud = rootView.findViewById(R.id.latitud);
        ubicacion = rootView.findViewById(R.id.tv_distancia);
        mas = rootView.findViewById(R.id.btn_mas);
        menos = rootView.findViewById(R.id.btn_menos);
        mapa = rootView.findViewById(R.id.mapa);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(rootView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(rootView.getContext(), "No hay permisos", Toast.LENGTH_SHORT).show();
        } else {
            //Ubicación
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitud.setText(String.valueOf(location.getLatitude()));
            longitud.setText(String.valueOf(location.getLongitude()));
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distancia += 1;
                ubicacion.setText(distancia+" Km");

            }
        });

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distancia > 1) {
                    distancia -= 1;
                    ubicacion.setText(distancia + " Km");
                }

            }
        });




        solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(rootView.getContext(), Categorias.class);
                i.putExtra("id_uc", id_uc);
                i.putExtra("latitud", lat);
                i.putExtra("longitud", lon);
                i.putExtra("distancia", distancia);
                startActivity(i);
            }
        });

        Location locationA = new Location("punto A");
        locationA.setLatitude(lat);
        locationA.setLongitude(lon);

        Location locationB = new Location("punto B");
        locationB.setLatitude(20.702978);
        locationB.setLongitude( -103.388983);
        float distance = locationA.distanceTo(locationB);
        distance = distance / 1000;
        Toast.makeText(getContext(), "d: " + distance, Toast.LENGTH_SHORT).show();
        return rootView;
    }

}
