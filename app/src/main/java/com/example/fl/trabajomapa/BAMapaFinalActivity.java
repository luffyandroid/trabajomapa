package com.example.fl.trabajomapa;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    ZOferta marcador, anuncio;
    LinearLayout LinearLayoutBuscar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamapa_final);


        //FLOATING BUTTON
        fab  = (FloatingActionsMenu) findViewById(R.id.menu_fab);



        SupportMapFragment vistaMapaFinal = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.vistaMapaFinal);

        vistaMapaFinal.getMapAsync(this);
        dbRef = FirebaseDatabase.getInstance().getReference();

        tvmarcador = (TextView) findViewById(R.id.tvmarcador);
        mensaje1 = (TextView) findViewById(R.id.mensaje1);
        mensaje2 = (TextView) findViewById(R.id.mensaje2);
        latocul = (TextView) findViewById(R.id.latocul);
        longocul = (TextView) findViewById(R.id.longocul);

        LinearLayoutBuscar = (LinearLayout) findViewById(R.id.LinearLayoutBuscar);


        //001 NO ESTOY SEGURO
        tvocultoba = (TextView)findViewById(R.id.tvocultoba);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraUpdate inicioSatelite =
                CameraUpdateFactory.newLatLngZoom(new LatLng(36.6178533,-6.3685917),5);
        mMap.moveCamera(inicioSatelite);
        dbRef.child("anuncios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //for(Marker marker:realTimeMarkers){
                //marker.remove();
                //}


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ZOferta ofe = snapshot.getValue(ZOferta.class);

                    //COMIENZA METODO VER FECHA
                    String fechaanuncio = ofe.getFecha().toString();
                    String subfechaanuncioano = fechaanuncio.substring(0,4);
                    int intsubfechaanuncioano = Integer.parseInt(subfechaanuncioano);
                    String subfechaanunciomes = fechaanuncio.substring(5,7);
                    int intsubfechaanunciomes = Integer.parseInt(subfechaanunciomes);
                    int intsubfechaanunciomes3meses = intsubfechaanunciomes+2;
                    String subfechaanunciodia = fechaanuncio.substring(8,10);
                    int intsubfechaanunciodia = Integer.parseInt(subfechaanunciodia);
                    String fechaactual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String subfechaactualano = fechaactual.substring(0,4);
                    int intsubfechaactualano = Integer.parseInt(subfechaactualano);
                    String subfechaactualmes = fechaactual.substring(5,7);
                    int intsubfechaactualmes = Integer.parseInt(subfechaactualmes);
                    String subfechaactualdia = fechaactual.substring(8,10);
                    int intsubfechaactualdia = Integer.parseInt(subfechaactualdia);

                    if(intsubfechaanunciomes3meses<intsubfechaactualmes && intsubfechaanunciodia<intsubfechaactualdia || intsubfechaanuncioano<intsubfechaactualano && intsubfechaanunciomes<intsubfechaactualmes && intsubfechaanunciodia<intsubfechaactualdia ||intsubfechaanuncioano<intsubfechaactualano && intsubfechaanunciomes==12 && intsubfechaactualmes>=3 && intsubfechaanunciodia<intsubfechaactualdia
                            || intsubfechaanuncioano<intsubfechaactualano && intsubfechaanunciomes==11 && intsubfechaactualmes>=2 && intsubfechaanunciodia<intsubfechaactualdia || intsubfechaanuncioano<intsubfechaactualano && intsubfechaanunciomes==10 && intsubfechaactualmes>=1 && intsubfechaanunciodia<intsubfechaactualdia){
                        dbRef.child("anuncios").child(ofe.getUid()).removeValue();
                    }else {

                        if (intsubfechaanunciomes < intsubfechaactualmes && intsubfechaanunciodia < intsubfechaactualdia || intsubfechaanuncioano < intsubfechaactualano && intsubfechaanunciodia < intsubfechaactualdia) {
                            ofe.setDisponible("borrar");
                            Map<String, Object> creacion = new HashMap<>();

                            creacion.put("disponible/", "borrar");
                            Toast.makeText(context, ofe.getUid(), Toast.LENGTH_SHORT).show();
                            dbRef.child("anuncios").child(ofe.getUid()).updateChildren(creacion);


                        }
                        //FINAL METODO VER FECHA
                        //if(ofe.getDisponible().equals("disponible"))
                        else {

                            latitud = ofe.getLatitud();
                            longitud = ofe.getLongitud();

                            ZMarcador info = new ZMarcador();
                            info.setNombre(ofe.getNombre());
                            info.setDetalles(ofe.getDetalles());
                            info.setSalario(ofe.getSalario());
                            info.setDireccion(ofe.getDireccion());
                            info.setTelefono(ofe.getTelefono());
                            info.setCorreo(ofe.getCorreo());

                            //ZAdaptadorVentanaInfo custominfowindow = new ZAdaptadorVentanaInfo(new ZAdaptadorVentanaInfo(getApplicationContext()))
                            //mMap.setInfoWindowAdapter(custominfowindow);

                            mMap.setInfoWindowAdapter
                                    (new ZAdaptadorVentanaInfo(BAMapaFinalActivity.this) {
                                        @Override
                                        public boolean onTouch(View view, MotionEvent motionEvent) {
                                            return false;
                                        }
                                    });


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

        //DIALOGO INFO ANUNCIO
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bamapa_infowindow);

        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/" + tvocultoba.getText().toString());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                anuncio = dataSnapshot.getValue(ZOferta.class);
                final TextView nombre = (TextView) dialog.findViewById(R.id.tvinfowindows_titulobb);
                final TextView detalles = (TextView) dialog.findViewById(R.id.tvinfowindows_detalles);
                final TextView salario = (TextView) dialog.findViewById(R.id.tvinfowindows_salario);
                final TextView direccion = (TextView) dialog.findViewById(R.id.tvinfowindows_direccion);
                final TextView telefono = (TextView) dialog.findViewById(R.id.tvinfowindows_telefono);
                final TextView correo = (TextView) dialog.findViewById(R.id.tvinfowindows_correo);
                final LinearLayout llprofesional = (LinearLayout) dialog.findViewById(R.id.LinearLayoutProfesion);

                nombre.setText(anuncio.getNombre());
                detalles.setText(anuncio.getDetalles());
                salario.setText(anuncio.getSalario());
                direccion.setText(anuncio.getDireccion());
                telefono.setText(anuncio.getTelefono());
                correo.setText(anuncio.getCorreo());

                //CAMBIAR DE COLOR EL BORDE SUPERIOR

                if (anuncio.getTipopuesto().equals("Otros")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#E84E1B"));
                }
                if (anuncio.getTipopuesto().equals("Administración")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#009EE2"));
                }
                if (anuncio.getTipopuesto().equals("Agricultura")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#DDDB00"));
                }
                if (anuncio.getTipopuesto().equals("Animación")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#FF394C"));
                }
                if (anuncio.getTipopuesto().equals("Atención al cliente")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#F29100"));
                }
                if (anuncio.getTipopuesto().equals("Calidad I+D")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#006633"));
                }
                if (anuncio.getTipopuesto().equals("Comercial")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#312782"));
                }
                if (anuncio.getTipopuesto().equals("Construcción")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#E84E1B"));
                }
                if (anuncio.getTipopuesto().equals("Diseño y Artes gráficas")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#E61B72"));
                }
                if (anuncio.getTipopuesto().equals("Educación")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#39A935"));
                }
                if (anuncio.getTipopuesto().equals("Farmaceutica")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#009540"));
                }
                if (anuncio.getTipopuesto().equals("Finanzas y banca")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#C4B316"));
                }
                if (anuncio.getTipopuesto().equals("Funerarias")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#1D1D1B"));
                }
                if (anuncio.getTipopuesto().equals("Hostelería")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#120F51"));
                }
                if (anuncio.getTipopuesto().equals("Informática")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#7FA50C"));
                }
                if (anuncio.getTipopuesto().equals("Ingeniería y técnico")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#634E42"));
                }
                if (anuncio.getTipopuesto().equals("Legal")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#7E4E24"));
                }
                if (anuncio.getTipopuesto().equals("Limpieza")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#0685B7"));
                }
                if (anuncio.getTipopuesto().equals("Logística y almacén")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#B17F49"));
                }
                if (anuncio.getTipopuesto().equals("Marketing y comunicaciones")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#E20613"));
                }
                if (anuncio.getTipopuesto().equals("Música")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#941B80"));
                }
                if (anuncio.getTipopuesto().equals("Profesiones y oficios")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#868686"));
                }
                if (anuncio.getTipopuesto().equals("Recursos humanos")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#1D70B7"));
                }
                if (anuncio.getTipopuesto().equals("Sanidad")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#BD1622"));
                }
                if (anuncio.getTipopuesto().equals("Transportes")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#6F6F6E"));
                }
                if (anuncio.getTipopuesto().equals("Turismo")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#E5332A"));
                }
                if (anuncio.getTipopuesto().equals("Venta")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#A2195B"));
                }
                if (anuncio.getTipopuesto().equals("Veterinaria")) {
                    llprofesional.setBackgroundColor(Color.parseColor("#683B11"));
                }




                final LinearLayout LayoutDetalles = (LinearLayout) dialog.findViewById(R.id.LayoutDetalless);
                final LinearLayout LayoutSalario = (LinearLayout) dialog.findViewById(R.id.LayoutSalarios);
                final LinearLayout LayoutDireccion = (LinearLayout) dialog.findViewById(R.id.LayoutDireccion);
                final LinearLayout LayoutTelefono = (LinearLayout) dialog.findViewById(R.id.LayoutTelefonos);
                final LinearLayout LayoutCorreo = (LinearLayout) dialog.findViewById(R.id.LayoutCorreos);

                LayoutDireccion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Uri location = Uri.parse("https://www.google.es/maps/@"+anuncio.getLatitud()+","+anuncio.getLongitud()+",20z");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                            startActivity(mapIntent);
                        }catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BAMapaFinalActivity.this, "No hay aplicación para ver la dirección", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                LayoutTelefono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Uri number = Uri.parse("tel:"+ telefono.getText().toString());
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                            startActivity(callIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BAMapaFinalActivity.this,
                                    "No hay apliación para llamar", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                LayoutCorreo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] TO = {correo.getText().toString()}; //001 AQUI DEBERÍA IR CORREO
                        String[] CC = {""};
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                            //finish();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BAMapaFinalActivity.this,
                                    "No tienes clientes de email instalados.", Toast.LENGTH_LONG).show();
                        }
                    }
                });



                if (detalles.equals("") ){
                    LayoutDetalles.setVisibility(View.GONE);
                }

                if (salario.equals("") ){
                    LayoutSalario.setVisibility(View.GONE);
                }

                if (telefono.equals("") ){
                    LayoutTelefono.setVisibility(View.GONE);
                }

                if (correo.equals("") ){
                    LayoutCorreo.setVisibility(View.GONE);
                }



                Button compartir = (Button) dialog.findViewById(R.id.btninfowindows_compartirBB);
                compartir.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String detalles_f, salario_f, telefono_f, correo_f;


                                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                                compartir.setType("text/plain");
                                //compartir.setType("text/plain");
                                String detalles_ = detalles.getText().toString();
                                String salario_ = salario.getText().toString();
                                String direccion_ = direccion.getText().toString();
                                String telefono_ = telefono.getText().toString();
                                String correo_ = correo.getText().toString();
                                String nombre_ = nombre.getText().toString();

                                //EN CASO DE QUE NO HAYA DATOS NO MANDE DATOS VACÍOS

                                if (detalles_.equals("") ){
                                    detalles_f = detalles_;}
                                else { detalles_f = "\uD83D\uDD38" + " " + detalles_ + "\n";
                                }

                                if (salario_.equals("")){
                                    salario_f = salario_;}
                                else { salario_f = "\uD83D\uDCB6" + " " + "salario: " + salario_ + "\n";
                                }

                                if (telefono_.equals("") ){
                                    telefono_f = telefono_;}
                                else { telefono_f = "\uD83D\uDCDE" + " " + telefono_ + "\n";
                                }

                                if (correo_.equals("") ){
                                    correo_f = correo_;}
                                else { correo_f = "\uD83D\uDCE7" + " " + correo_ + "\n";
                                }

                                String en = "Enviado desde GeoWork";

                                //TEXTO CON ESTILO

                                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compartir oferta de" + nombre_);
                                compartir.putExtra(android.content.Intent.EXTRA_TEXT, ("Empleo de " + nombre_ + "\n"
                                        + detalles_f
                                        + salario_f
                                        + "\uD83D\uDCCD" + " " + direccion_ + "\n"
                                        + telefono_f
                                        + correo_f
                                        + en));
                                //compartir.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtm"Oferta de " + nombre_ );
                                startActivity(Intent.createChooser(compartir, "Compartir vía"));

                            }



                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginActivity", "DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);
        dialog.show();

    }




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
        fab.collapse();
    }

    public void clickpublicar(View v) {
        Intent mainIntent = new Intent().setClass(
                BAMapaFinalActivity.this, CAPublicarOfertaActivity.class);
        startActivity(mainIntent);
        finish();
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

    public void clickbuscar(View v) {
        LinearLayoutBuscar.setVisibility(View.VISIBLE);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clickbuscarbuscar(View v) {
        LinearLayoutBuscar.setVisibility(View.GONE);
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    public void clickinfo(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bamapa_info);

        TextView tvDialog = (TextView) dialog.findViewById(R.id.tvDialog);
        TextView tvDialogCorreo = (TextView) dialog.findViewById(R.id.tvDialogCorreo);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);

        tvDialog.setText(Html.fromHtml(getString(R.string.Infomensaje)));


        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        tvDialogCorreo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String[] TO = {"fulgenll@hotmail.com"};
                        String[] CC = {""};
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                            //finish();
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(BAMapaFinalActivity.this,
                                    "No tienes clientes de email instalados.", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        dialog.show();
        //PARA QUE SE CIERRE AL PULSAR
        fab.collapse();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Salir de la aplicación")
                .setMessage("¿Seguro que desea salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO accion de salir de la app
                        finishAffinity();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO accion de quedarse en el menu
                    }
                })
                .create().show();
    }
}
