package com.example.fl.trabajomapa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fl.trabajomapa.Config.ZDialog;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BAMapaFinalActivity extends AppCompatActivity implements OnMapReadyCallback {


    FloatingActionsMenu fab;
    // private FloatingActionButton menu_fab;
    static final String EXTRA_ANUNCIO = "ANUNCIO";
    private GoogleMap mMap;

    final Context context = this;

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
    ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    Double latitud, longitud;
    TextView tvmarcador, mensaje1, mensaje2, tvocultoba, latocul, longocul;
    Button normal, satelite, hibrido, locactual, btninfowindow_compartir;
    //002 Button btninfowindow_compartir;
    ZOferta marcador, anuncio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamapa_final);


       //002 btninfowindow_compartir = (Button) findViewById(R.id.btninfowindow_compartir);
       //FLOATING BUTTON
        fab  = (FloatingActionsMenu) findViewById(R.id.menu_fab);



        /*
        final FloatingActionButton botonBAMapanormal = (FloatingActionButton) findViewById(R.id.botonBAMapanormal);
        botonBAMapanormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //PARA QUE SE CIERRE AL PULSAR
              //  fab.collapse();
            }
        });
        */

        /* //008

        //BOTON FLOTANTE
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        */


        SupportMapFragment vistaMapaFinal = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.vistaMapaFinal);
/*
        //002 QUE SE CIERRE EL FLOAT MENU CUANDO PULSE FUERA DEL MENU
        vistaMapaFinal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.d(TAG, "onTouch  " + "");
                if (fab.isExpanded()) {
                    fab.collapse();
                    return true;
                }
                return false;

            }
        });
        */
        vistaMapaFinal.getMapAsync(this);
        dbRef = FirebaseDatabase.getInstance().getReference();

        tvmarcador = (TextView) findViewById(R.id.tvmarcador);
        mensaje1 = (TextView) findViewById(R.id.mensaje1);
        mensaje2 = (TextView) findViewById(R.id.mensaje2);
        latocul = (TextView) findViewById(R.id.latocul);
        longocul = (TextView) findViewById(R.id.longocul);


        //001 NO ESTOY SEGURO
        tvocultoba = (TextView)findViewById(R.id.tvocultoba);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        //BOTONES DEL FLOAT MENU
        //008

        /*

        View Btnsatelite, Btnnormal, Btnhibrido, Btnlocalizacion, Btnpublicar, Btnrenovar, Btninfo;

        Btnsatelite = findViewById(R.id.botonBAMapasatelite);
        Btnnormal = findViewById(R.id.botonBAMapanormal);
        Btnhibrido = findViewById(R.id.botonBAMapahibrido);
        Btnlocalizacion = findViewById(R.id.botonBAMapaloc);
        Btnpublicar = findViewById(R.id.botonBAMapaPublicar);
        Btnrenovar = findViewById(R.id.botonBAMapaRenovar);
        Btninfo = findViewById(R.id.botonBAMapaInfo);

        //FLOATING BUTTON Y SUS DIFERENTES SECCIONES
        fab  = (FloatingActionsMenu) findViewById(R.id.menu_fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //003  Btnsatelite.getVisibility(View.VISIBLE);
                fab.showContextMenu();

                //Btnsatelite.setVisibility(View.VISIBLE);

                //001 PARA QUE CIERRE SI PULSO FUERA
                //fab.setClosedOnTouchOutside(true);
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }

        });
        Btnsatelite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }
        });
        Btnnormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }
        });
        Btnhibrido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }
        });
        Btnlocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationStart();
            }
        });
        Btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent().setClass(
                        BAMapaFinalActivity.this, CAPublicarOfertaActivity.class);
                startActivity(mainIntent);
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }
        });
        Btnrenovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent().setClass(
                        BAMapaFinalActivity.this, DAListaAnunciosActivity.class);
                startActivity(mainIntent);
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }
        });
        Btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_bamapa_info);

                TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);

                volvermenu.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                dialog.show();
                //PARA QUE SE CIERRE AL PULSAR
                fab.collapse();
            }
        });*/
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        dbRef.child("anuncios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //for(Marker marker:realTimeMarkers){
                //marker.remove();
                //}


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ZOferta ofe = snapshot.getValue(ZOferta.class);

                    if(ofe.getDisponible().equals("disponible")) {

                        latitud = ofe.getLatitud();
                        longitud = ofe.getLongitud();

                        ZMarcador info = new ZMarcador();
                        info.setNombre(ofe.getNombre());
                        info.setDetalles(ofe.getDetalles());
                        info.setSalario(ofe.getSalario());
                        info.setDireccion(ofe.getDireccion());
                        info.setTelefono(ofe.getTelefono());
                        info.setCorreo(ofe.getCorreo());

                        //ZAdaptadorVentanaInfo custominfowindow = new ZAdaptadorVentanaInfo(getApplicationContext());
                        //mMap.setInfoWindowAdapter(custominfowindow);

                    /*mMap.setInfoWindowAdapter
                            (new ZAdaptadorVentanaInfo(BAMapaFinalActivity.this) {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    return false;
                                }
                            });*/


                        MarkerOptions markerOptions = new MarkerOptions();
                        if (ofe.getTipopuesto().equals("Otros")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_otros));
                        }
                        if (ofe.getTipopuesto().equals("Administración")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_administracion));
                        }
                        if (ofe.getTipopuesto().equals("Agricultura")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_agricultura));
                        }
                        if (ofe.getTipopuesto().equals("Animación")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_animacion));
                        }
                        if (ofe.getTipopuesto().equals("Atención al cliente")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_atencionalcliente));
                        }
                        if (ofe.getTipopuesto().equals("Calidad I+D")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_calidadimasd));
                        }
                        if (ofe.getTipopuesto().equals("Comercial")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_comercial));
                        }
                        if (ofe.getTipopuesto().equals("Construcción")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_construccion));
                        }
                        if (ofe.getTipopuesto().equals("Diseño y Artes gráficas")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_disenoyartesgraficas));
                        }
                        if (ofe.getTipopuesto().equals("Educación")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_educacion));
                        }
                        if (ofe.getTipopuesto().equals("Farmaceutica")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_famaceutica));
                        }
                        if (ofe.getTipopuesto().equals("Finanzas y banca")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_finanzasybanca));
                        }
                        if (ofe.getTipopuesto().equals("Funerarias")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_funerarias));
                        }
                        if (ofe.getTipopuesto().equals("Hostelería")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_hosteleria));
                        }
                        if (ofe.getTipopuesto().equals("Informática")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_informatica));
                        }
                        if (ofe.getTipopuesto().equals("Ingeniería y técnico")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_ingenieriaytecnicos));
                        }
                        if (ofe.getTipopuesto().equals("Legal")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_legal));
                        }
                        if (ofe.getTipopuesto().equals("Limpieza")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_limpeza));
                        }
                        if (ofe.getTipopuesto().equals("Logística y almacén")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_logisticayalmacen));
                        }
                        if (ofe.getTipopuesto().equals("Marketing y comunicaciones")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_marketingycomunicaciones));
                        }
                        if (ofe.getTipopuesto().equals("Música")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_musica));
                        }
                        if (ofe.getTipopuesto().equals("Profesiones y oficios")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_profesionesyoficios));
                        }
                        if (ofe.getTipopuesto().equals("Recursos humanos")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_recursoshumanos));
                        }
                        if (ofe.getTipopuesto().equals("Sanidad")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_sanidad));
                        }
                        if (ofe.getTipopuesto().equals("Transportes")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_transportes));
                        }
                        if (ofe.getTipopuesto().equals("Turismo")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_turismo));
                        }
                        if (ofe.getTipopuesto().equals("Venta")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_venta));
                        }
                        if (ofe.getTipopuesto().equals("Veterinaria")) {
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_veterinaria));
                        }
                        markerOptions.title(ofe.getUid());
                        markerOptions.position(new LatLng(latitud, longitud));
                        tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));


                    }

                }

                //realTimeMarkers.clear();
                //realTimeMarkers.addAll(tmpRealTimeMarkers);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String idmarcador = marker.getTitle().toString();
                tvocultoba.setText(idmarcador);
                pasarinfo();


                //Toast.makeText(BAMapaFinalActivity.this, idmarcador, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }


    //COMPARTIR EN REDES SOCIALES

    //001
            /*
    btninfowindow_compartir.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            */

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

        //mensaje1.setText("Localizacion agregada");
        //mensaje2.setText("");
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
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);

                if (!list.isEmpty()) {
                    /*Address DirCalle = list.get(0);
                    mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));*/
                        if(mensaje1.getText().equals("si")) {
                            CameraUpdate locactual =
                                    CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 17);
                            mMap.animateCamera(locactual);
                            mensaje1.setText("no");
                        }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        BAMapaFinalActivity baMapaFinalActivity;

        public BAMapaFinalActivity getBaMapaFinalActivity() {
            return baMapaFinalActivity;
        }

        public void setMainActivity(BAMapaFinalActivity baMapaFinalActivity) {
            this.baMapaFinalActivity = baMapaFinalActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

                loc.getLatitude();
                loc.getLongitude();


            this.baMapaFinalActivity.setLocation(loc);


        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            //mensaje1.setText("GPS Desactivado");

        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            //mensaje1.setText("GPS Activado");

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
            }
        }
    }

    private void pasarinfo() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/" + tvocultoba.getText().toString());
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                anuncio = dataSnapshot.getValue(ZOferta.class);
                Intent i = new Intent().setClass(BAMapaFinalActivity.this, BBInfoAnuncio.class);
                i.putExtra(EXTRA_ANUNCIO, anuncio);
                startActivity(i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginActivity", "DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);
    }

    /*public void compartir (View v) {
//002
/*
    public void compartir(View v) {


        Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
        compartir.setType("text/plain");

        TextView nombre = v.findViewById(R.id.tvinfowindow_titulo);
        TextView detalle = v.findViewById(R.id.tvnfowindow_detalles);
        TextView salario = v.findViewById(R.id.tvnfowindow_salario);
        TextView direccion = v.findViewById(R.id.tvnfowindow_direccion);
        TextView telefono = v.findViewById(R.id.tvnfowindow_telefono);
        TextView correo = v.findViewById(R.id.tvnfowindow_correo);

        nombre.getText().toString();
        detalle.getText().toString();
        salario.getText().toString();
        direccion.getText().toString();
        telefono.getText().toString();
        correo.getText().toString();


        compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Empleo encontrado en GeoWork " + nombre);
        compartir.putExtra(android.content.Intent.EXTRA_TEXT, (Parcelable) nombre);
        startActivity(Intent.createChooser(compartir, "Compartir vía"));

    }
*/



//ONCLICK FLOAT BUTTON

    public void clicksatelite(View v) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clicknormal(View v) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clickhibrido(View v) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clicklocalizacion(View v) {
        mensaje1.setText("si");
        locationStart();

        //009 DONDE Y QUE PONER onStop();
        fab.collapse();
    }

    public void clickpublicar(View v) {
        Intent mainIntent = new Intent().setClass(
                BAMapaFinalActivity.this, CAPublicarOfertaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clickrenovar(View v) {
        Intent mainIntent = new Intent().setClass(
                BAMapaFinalActivity.this, DAListaAnunciosActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clickinfo(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bamapa_info);

        //TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);


        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.show();
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

}
