package com.example.fl.trabajomapa;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BBCallesFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap calles;
    ZOferta usu=null;

    ArrayList<ZOferta> listaRecetas = new ArrayList<ZOferta>();

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

    public BBCallesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bbcalles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment vistaCalles = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.vistaCalles);
        vistaCalles.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        calles = googleMap;

        //PUNTO DE INICIO DEL MAPA
        LatLng inicio = new LatLng(36.6178533,-6.3685917);
        CameraUpdate inicioCalles =
                CameraUpdateFactory.newLatLngZoom(new LatLng(36.6178533,-6.3685917),5);
        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ZOferta oferta = dataSnapshot.getValue(ZOferta.class);
                Double latitud = oferta.getLatitud();
                Double longitud = oferta.getLongitud();
                LatLng prueba = new LatLng(latitud, longitud);
                //Toast.makeText(getContext(), oferta.nombre, Toast.LENGTH_SHORT).show();
                MarkerOptions option = new MarkerOptions();
                option.position(prueba).title(oferta.getNombre());
                calles.addMarker(option);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        calles.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        calles.moveCamera(inicioCalles);
    }
}
