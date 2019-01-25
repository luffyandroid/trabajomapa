package com.example.fl.trabajomapa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fl.trabajomapa.Config.ZDialog;
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

import java.util.ArrayList;

public class BAMapaFinalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
    ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    Double latitud, longitud;
    TextView tvmarcador;

    ZOferta marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamapa_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SupportMapFragment vistaMapaFinal = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.vistaMapaFinal);
        vistaMapaFinal.getMapAsync(this);
        dbRef = FirebaseDatabase.getInstance().getReference();

        tvmarcador = (TextView)findViewById(R.id.tvmarcador);


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



                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ZOferta ofe = snapshot.getValue(ZOferta.class);
                    latitud = ofe.getLatitud();
                    longitud = ofe.getLongitud();

                    ZMarcador info = new ZMarcador();
                    info.setNombre(ofe.getNombre());
                    info.setDetalles(ofe.getDetalle());
                    info.setSalario(ofe.getSalario());
                    info.setDireccion(ofe.getDireccion());
                    info.setTelefono(ofe.getTelefono());
                    info.setCorreo(ofe.getCorreo());

                    //ZAdaptadorVentanaInfo custominfowindow = new ZAdaptadorVentanaInfo(getApplicationContext());
                    //mMap.setInfoWindowAdapter(custominfowindow);
                    mMap.setInfoWindowAdapter(new ZAdaptadorVentanaInfo(BAMapaFinalActivity.this));



                    MarkerOptions markerOptions = new MarkerOptions();
                    if (ofe.getTipopuesto().equals("Otros")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_otros));
                    }
                    if (ofe.getTipopuesto().equals("Administración")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_administracion));
                    }
                    if (ofe.getTipopuesto().equals("Agricultura")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_agricultura));
                    }
                    if (ofe.getTipopuesto().equals("Animación")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_animacion));
                    }
                    if (ofe.getTipopuesto().equals("Atención al cliente")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_atencionalcliente));
                    }
                    if (ofe.getTipopuesto().equals("Calidad I+D")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_calidadimasd));
                    }
                    if (ofe.getTipopuesto().equals("Comercial")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_comercial));
                    }
                    if (ofe.getTipopuesto().equals("Construcción")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_construccion));
                    }
                    if (ofe.getTipopuesto().equals("Diseño y Artes gráficas")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_disenoyartesgraficas));
                    }
                    if (ofe.getTipopuesto().equals("Educación")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_educacion));
                    }
                    if (ofe.getTipopuesto().equals("Farmaceutica")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_famaceutica));
                    }
                    if (ofe.getTipopuesto().equals("Finanzas y banca")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_finanzasybanca));
                    }
                    if (ofe.getTipopuesto().equals("Funerarias")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_funerarias));
                    }
                    if (ofe.getTipopuesto().equals("Hostelería")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_hosteleria));
                    }
                    if (ofe.getTipopuesto().equals("Informática")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_informatica));
                    }
                    if (ofe.getTipopuesto().equals("Ingeniería y técnico")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_ingenieriaytecnicos));
                    }
                    if (ofe.getTipopuesto().equals("Legal")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_legal));
                    }
                    if (ofe.getTipopuesto().equals("Limpieza")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_limpeza));
                    }
                    if (ofe.getTipopuesto().equals("Logística y almacén")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_logisticayalmacen));
                    }
                    if (ofe.getTipopuesto().equals("Marketing y comunicaciones")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_marketingycomunicaciones));
                    }
                    if (ofe.getTipopuesto().equals("Música")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_musica));
                    }
                    if (ofe.getTipopuesto().equals("Profesiones y oficios")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_profesionesyoficios));
                    }
                    if (ofe.getTipopuesto().equals("Recursos humanos")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_recursoshumanos));
                    }
                    if (ofe.getTipopuesto().equals("Sanidad")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_sanidad));
                    }
                    if (ofe.getTipopuesto().equals("Transportes")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_transportes));
                    }
                    if (ofe.getTipopuesto().equals("Turismo")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_turismo));
                    }
                    if (ofe.getTipopuesto().equals("Venta")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_venta));
                    }
                    if (ofe.getTipopuesto().equals("Veterinaria")){
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_veterinaria));
                    }
                    markerOptions.title(ofe.getUid());
                    markerOptions.position(new LatLng(latitud, longitud));
                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));

                }

                //realTimeMarkers.clear();
                //realTimeMarkers.addAll(tmpRealTimeMarkers);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
