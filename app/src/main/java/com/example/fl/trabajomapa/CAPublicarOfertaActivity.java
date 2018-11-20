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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import android.widget.AdapterView.OnItemSelectedListener;


import java.io.IOException;

import java.util.Locale;
import java.util.regex.Pattern;

public class CAPublicarOfertaActivity extends AppCompatActivity {

    //DECLARO VARIANTES

    Spinner spincategoriaCA;

    TextView tvocultoCA;
    
    EditText etnombrepuestoCA, etdetallespuestoCA, etsalariopuestoCA,
            etdireccionnegocioCA, ettelefononegocioCA, etcorreonegocioCA;

    CheckBox checkpoliticaCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capublicar_oferta);

        //TOAST COMPROBACIÓN SI HAY INTERNET
        if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(),
                    "Comprueba tu conexión a Internet", Toast.LENGTH_LONG)
                    .show();
        }

        //VARIANTES DEL SPINNER
        List<ZSpinnerCategoria> items = new ArrayList<ZSpinnerCategoria>(15);
        items.add(new ZSpinnerCategoria(getString(R.string.Otros), R.drawable.map_marker_otros));
        items.add(new ZSpinnerCategoria(getString(R.string.Administracion), R.drawable.map_marker_administracion));
        items.add(new ZSpinnerCategoria(getString(R.string.Agricultura), R.drawable.map_marker_agricultura));
        items.add(new ZSpinnerCategoria(getString(R.string.Animacion), R.drawable.map_marker_animacion));
        items.add(new ZSpinnerCategoria(getString(R.string.Atencionalcliente), R.drawable.map_marker_atencionalcliente));
        items.add(new ZSpinnerCategoria(getString(R.string.CalidadID), R.drawable.map_marker_calidadimasd));
        items.add(new ZSpinnerCategoria(getString(R.string.Comercial), R.drawable.map_marker_comercial));
        items.add(new ZSpinnerCategoria(getString(R.string.Construccion), R.drawable.map_marker_construccion));
        items.add(new ZSpinnerCategoria(getString(R.string.DisenoyArtesgraficas), R.drawable.map_marker_disenoyartesgraficas));
        items.add(new ZSpinnerCategoria(getString(R.string.Educacion), R.drawable.map_marker_educacion));
        items.add(new ZSpinnerCategoria(getString(R.string.Farmaceutica), R.drawable.map_marker_famaceutica));
        items.add(new ZSpinnerCategoria(getString(R.string.Finanzasybanca), R.drawable.map_marker_finanzasybanca));
        items.add(new ZSpinnerCategoria(getString(R.string.Funerarias), R.drawable.map_marker_funerarias));
        items.add(new ZSpinnerCategoria(getString(R.string.Hosteleria), R.drawable.map_marker_hosteleria));
        items.add(new ZSpinnerCategoria(getString(R.string.Informatica), R.drawable.map_marker_informatica));
        items.add(new ZSpinnerCategoria(getString(R.string.Ingenieriaytecnico), R.drawable.map_marker_ingenieriaytecnicos));
        items.add(new ZSpinnerCategoria(getString(R.string.Legal), R.drawable.map_marker_legal));
        items.add(new ZSpinnerCategoria(getString(R.string.Limpeza), R.drawable.map_marker_limpeza));
        items.add(new ZSpinnerCategoria(getString(R.string.Logisticayalmacen), R.drawable.map_marker_logisticayalmacen));
        items.add(new ZSpinnerCategoria(getString(R.string.Marketingycomunicaciones), R.drawable.map_marker_marketingycomunicaciones));
        items.add(new ZSpinnerCategoria(getString(R.string.Musica), R.drawable.map_marker_musica));
        items.add(new ZSpinnerCategoria(getString(R.string.Profesionesyoficios), R.drawable.map_marker_profesionesyoficios));
        items.add(new ZSpinnerCategoria(getString(R.string.Recursoshumanos), R.drawable.map_marker_recursoshumanos));
        items.add(new ZSpinnerCategoria(getString(R.string.Sanidad), R.drawable.map_marker_sanidad));
        items.add(new ZSpinnerCategoria(getString(R.string.Transportes), R.drawable.map_marker_transportes));
        items.add(new ZSpinnerCategoria(getString(R.string.Turismo), R.drawable.map_marker_turismo));
        items.add(new ZSpinnerCategoria(getString(R.string.Venta), R.drawable.map_marker_venta));
        items.add(new ZSpinnerCategoria(getString(R.string.Veterinaria), R.drawable.map_marker_veterinaria));

        //TODAS LAS MIERDAS DEL SPINNER
        spincategoriaCA = (Spinner) findViewById(R.id.spincategoriaCA);
        spincategoriaCA.setAdapter(new ZSpinnerCategoriaAdaptador(this,items));
        spincategoriaCA.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                Toast.makeText(adapterView.getContext(), ((ZSpinnerCategoria) adapterView.getItemAtPosition(position)).getNombre(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //nothing
            }
        });



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



    //COMPROBACIÓN CONEXIÓN INTERNET

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

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
        String correonegocioCA = etcorreonegocioCA.getText().toString();

        //COMPROBAR SI LOS CAMPOS NO ESTAN VACIOS
        if (nombrepuestoCA.equals("") || direccionnegocioCA.equals("")) {

            Toast.makeText(getApplicationContext(),
                    "Rellena los campos obligatorios",
                    Toast.LENGTH_LONG).show();

        } else {

            boolean error = false;

            //VALIDAR SALARIO, TELEFONO E MAIL

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

            //VALIDAR MAIL
            if (!Pattern.matches("[a-zA-Z0-9._-]+@[a-z0-9]+[.]+[a-z]+", correonegocioCA)) {
                etcorreonegocioCA.setError("Correo no válido");
                error = true;
            }

            //00 AQUI PONER COMPROBACIÓN CHECK



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
