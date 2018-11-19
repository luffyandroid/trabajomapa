package com.example.fl.trabajomapa;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CAPublicarOfertaActivity extends AppCompatActivity {

    //DECLARO VARIANTES
    TextView tvocultoCA;
    
    EditText etnombrepuestoCA, etdetallespuestoCA, etsalariopuestoCA,
            etdireccionnegocioCA, ettelefononegocioCA, etcorreonegocioCA;

    CheckBox checkpoliticaCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capublicar_oferta);

        //ENLAZO VARIANTES
        etnombrepuestoCA = (EditText) findViewById(R.id.etnombrepuestoCA);
        etdetallespuestoCA = (EditText) findViewById(R.id.etdetallespuestoCA);
        etsalariopuestoCA = (EditText) findViewById(R.id.etsalariopuestoCA);
        etdireccionnegocioCA = (EditText) findViewById(R.id.etdireccionnegocioCA);
        ettelefononegocioCA = (EditText) findViewById(R.id.ettelefononegocioCA);
        etcorreonegocioCA = (EditText) findViewById(R.id.etcorreonegocioCA);

        tvocultoCA = (TextView) findViewById(R.id.tvocultoCA);

        checkpoliticaCA = (CheckBox) findViewById(R.id.checkpoliticaCA);

    }//FIN ONCREATE

    //BOTON OBTENER DIRECCION
    public void obtenerdireccion (View view){
        locationStart();
    }

    //MANDAR OFERTA
    public void publicaroferta(View view) {

        //ENLAZO VARIANTES CAMPOS OBLIGATORIOS
        String nombrepuestoCA = etnombrepuestoCA.getText().toString();
        String salariopuestoCA = etsalariopuestoCA.getText().toString();
        String direccionnegocioCA = etdireccionnegocioCA.getText().toString();
        String telefononegocioCA = ettelefononegocioCA.getText().toString();

        //COMPROBAR SI LOS CAMPOS NO ESTAN VACIOS
        if (nombrepuestoCA.equals("") || direccionnegocioCA.equals("")) {

            Toast.makeText(getApplicationContext(),
                    "Rellena los campos obligatorios",
                    Toast.LENGTH_LONG).show();

        } else {

            boolean error = false;

            //VALIDAR SALARIO Y TELEFONO

            //VALIDAR SALARIO
            if (!Pattern.matches("^[0-9]{9}$", salariopuestoCA)) {
                etsalariopuestoCA.setError("Valores en € completos");
                error = true;
            }

            //VALIDAR TELEFONO
            if (!Pattern.matches("^[0-9]{9}$", telefononegocioCA)) {
                ettelefononegocioCA.setError("Teléfono no válido");
                error = true;
            }

            if (error) {

                Toast.makeText(getApplicationContext(), "Comprueba que los datos son correctos datos", Toast.LENGTH_LONG).show();


            }


        }
    }

    //PROTOCOLO PARA OBTENER DIRECCION ▼
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        tvocultoCA.setText("Localizacion agregada");
        etdireccionnegocioCA.setText("");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //OBTENER LA DIRECCION DE LA CALLE A PARTIR DE LA LATITUD Y LA LONGITUD
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    etdireccionnegocioCA.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //COMIENZA GESTION DE LOCALIZACION
    public class Localizacion implements LocationListener {
        CAPublicarOfertaActivity CAPublicarOfertaActivity;
        public CAPublicarOfertaActivity getCAPublicarOfertaActivity() {
            return CAPublicarOfertaActivity;
        }
        public void setMainActivity(CAPublicarOfertaActivity CAPublicarOfertaActivity) {
            this.CAPublicarOfertaActivity = CAPublicarOfertaActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {

            // ESTE METODO SE EJECUTA CADA VEZ QUE EL GPS RECIBE NUEVAS COORDENADAS DEBIDO A LA DETECCION DE UN CAMBIO DE UBICACION
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Lat = "+ loc.getLatitude() + "\n Long = " + loc.getLongitude();
            tvocultoCA.setText(Text);
            this.CAPublicarOfertaActivity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // AVISO DE GPS DESACTIVADO
            tvocultoCA.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {

            // AVISO DE GPS ACTIVO
            tvocultoCA.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;

                //PROTOCOLO PARA OBTENER DIRECCION ▲
            }
        }
    }


}
